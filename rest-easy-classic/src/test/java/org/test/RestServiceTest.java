package org.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartOutput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.test.model.MultipartBody;
import org.test.service.RestService;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(TestWireMockServer.class)
@TestMethodOrder(OrderAnnotation.class)
public class RestServiceTest {

    @Inject
    @RestClient
    RestService restService;

    /**
     * [OK] multipart/form-data , use annotation.
     */
    @Test
    @Order(1)
    @DisplayName("multipart/form-data, use annotation.")
    public void testCase1() {
        MultipartBody body = createMultipartBody();
        String response = restService.postMultipartFormData(body);

        assertEquals("request matched", response);
    }

    /**
     * [OK] multipart/* , use annotation.
     */
    @Test
    @Order(2)
    @DisplayName("multipart/*, use annotation.")
    public void testCase2() {
        MultipartBody body = createMultipartBody();
        String response = restService.postMultipartAsterisk(body);

        assertEquals("request matched", response);
    }

    /**
     * [NG] multipart/mixed, use annotation.
     * Error: RESTEASY003215: could not find writer for content-type multipart/mixed
     */
    @Test
    @Order(3)
    @DisplayName("multipart/mixed, use annotation.")
    public void testCase3() {

        MultipartBody body = createMultipartBody();
        String response = restService.postMultipartMixed(body);

        assertEquals("request matched", response);
    }

    /**
     * [OK] multipart/form-data , not use annotation.
     */
    @Test
    @Order(4)
    @DisplayName("multipart/form-data, not use annotation.")
    public void testCase4() {
        MultipartBody body = createMultipartBody();

        MultipartFormDataOutput output = new MultipartFormDataOutput();
        output.addFormData("file", body.file, MediaType.APPLICATION_OCTET_STREAM_TYPE);
        output.addFormData("text1", body.text1, MediaType.TEXT_PLAIN_TYPE);
        output.addFormData("text2", body.text2, MediaType.TEXT_PLAIN_TYPE);

        String response = restService.postMultipartFormDataManual(output);

        assertEquals("request matched", response);
    }

    /**
     * [NG] multipart/mixed , not use annotation.
     * Error: WireMock: [Multipart request body] is not present
     */
    @Test
    @Order(5)
    @DisplayName("multipart/mixed, not use annotation.")
    public void testCase5() {
        MultipartBody body = createMultipartBody();

        MultipartOutput output = new MultipartOutput();
        output.addPart(body.file, MediaType.APPLICATION_OCTET_STREAM_TYPE, "file");
        output.addPart(body.text1, MediaType.APPLICATION_OCTET_STREAM_TYPE, "text1");
        output.addPart(body.text2, MediaType.APPLICATION_OCTET_STREAM_TYPE, "text2");

        String response = restService.postMultipartMixedManual(output);

        assertEquals("request matched", response);
    }

    private MultipartBody createMultipartBody() {
        MultipartBody body = new MultipartBody();
        body.text1 = "text1";
        body.text2 = "text2";
        body.file = "file contents.".getBytes(StandardCharsets.UTF_8);
        return body;
    }

}