package com.api.automation.utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class WireMockManager {

    private static WireMockServer server;

    public static void start() {
        ConfigReader config = new ConfigReader();

        int port = config.getInt("wiremockport", 9898);
        String wiremockUrl = config.get("wiremockurl", "http://localhost:9898");

        System.setProperty("wiremockurl", wiremockUrl);

        server = new WireMockServer(
            options()
                .port(port)
                .usingFilesUnderClasspath("datafiles/wiremock")
        );
        server.start();
        System.out.println("WireMock started on port " + port + " [url: " + wiremockUrl + "]");
    }

    public static void stop() {
        if (server != null) {
            server.stop();
        }
    }
}
