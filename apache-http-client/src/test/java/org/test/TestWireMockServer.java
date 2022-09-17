package org.test;

import static com.github.tomakehurst.wiremock.client.WireMock.aMultipart;
import static com.github.tomakehurst.wiremock.client.WireMock.binaryEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.util.Map;

import org.apache.hc.core5.http.HttpHeaders;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class TestWireMockServer implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer = new WireMockServer(options().port(8099));

    @Override
    public Map<String, String> start() {
        wireMockServer.start();

        // for Multipart Request
        wireMockServer.stubFor(post("/apache-http-client")
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("multipart/mixed"))
                .withMultipartRequestBody(
                        aMultipart().withName("file")
                                .withBody(binaryEqualTo("file contents.".getBytes())))
                .withMultipartRequestBody(
                        aMultipart().withName("text1").withBody(equalTo("text1")))
                .withMultipartRequestBody(
                        aMultipart().withName("text2").withBody(equalTo("text2")))
                .willReturn(ok("request matched")));

        return Map.of("base-url", wireMockServer.baseUrl());
    }

    @Override
    public void stop() {
        wireMockServer.stop();
    }
}
