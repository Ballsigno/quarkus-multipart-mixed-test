package org.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(TestWireMockServer.class)
@TestMethodOrder(OrderAnnotation.class)
class ApacheHttpClientTest {

    /**
     * [OK] multipart/mixed , use Apache HTTP Client.
     *
     * @throws IOException    ioException...
     * @throws ParseException paraseException...
     */
    @Test
    @Order(1)
    @DisplayName("multipart/mixed, use Apache HTTP Client.")
    void testCase1() throws IOException, ParseException {
        String responseBody = "";

        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            // prepare request
            final HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .setMimeSubtype("mixed")
                    .addBinaryBody("file", "file contents.".getBytes(StandardCharsets.UTF_8))
                    .addTextBody("text1", "text1")
                    .addTextBody("text2", "text2")
                    .build();
            final HttpPost httppost = new HttpPost("http://localhost:8099" +
                    "/apache-http-client");
            httppost.setEntity(reqEntity);

            // do request
            try (final CloseableHttpResponse response = httpclient.execute(httppost)) {
                responseBody = EntityUtils.toString(response.getEntity());
            }
        }

        assertEquals("request matched", responseBody);
    }

}