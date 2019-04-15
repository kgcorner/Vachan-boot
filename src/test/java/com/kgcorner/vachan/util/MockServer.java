package com.kgcorner.vachan.util;

/*
Description : Provides utility class for creating a mock server
Author: kumar
Created on : 15/4/19
*/

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.apache.log4j.Logger;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

public class MockServer {
    private static WireMockServer mockServer;
    private static WireMockConfiguration wireMockConfiguration;
    private static final Logger LOGGER = Logger.getLogger(MockServer.class);
    private static final int PORT = 9090;


    /**
     * Creates a mock server on given port
     */
    public static void createServer() {
        if(mockServer == null) {
            mockServer = new WireMockServer(WireMockConfiguration.options().port(PORT));
            mockServer.start();
            configureFor(PORT);
        }
    }

    /**
     * Registers a new endpoint for GET request on mocked server
     * @param urlPath path of the end point
     * @param queries queries if any
     * @param responseBody response body to be sent
     */
    public static void mockNewUrl(String urlPath, Map<String, String> queries, String responseBody) {
        MappingBuilder mappingBuilder = get(urlPathEqualTo(urlPath));
        if(queries != null) {
            for (Map.Entry<String, String> entry : queries.entrySet()) {
                mappingBuilder.withQueryParam(entry.getKey(), matching(entry.getValue()));
            }
        }
        mappingBuilder.willReturn(aResponse().withBody(responseBody).withHeader("content-type",
            "application/json; charset=utf-8"));
        StubMapping stubMapping = stubFor(mappingBuilder);
        mockServer.addStubMapping(stubMapping);
    }

    /**
     * Shuts down the server
     */
    public static void shutDown() {
        mockServer.shutdown();
    }
}