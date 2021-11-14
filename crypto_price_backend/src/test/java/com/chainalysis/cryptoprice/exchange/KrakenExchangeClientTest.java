package com.chainalysis.cryptoprice.exchange;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class KrakenExchangeClientTest {
    private MockWebServer mockWebServer;
    private KrakenExchangeClient krakenExchangeClient;

    @BeforeEach
    void setupMockWebServer() {
        mockWebServer = new MockWebServer();

        KrakenExchangeProperties properties = new KrakenExchangeProperties();
        properties.setBaseUrl(mockWebServer.url("/").url().toString());
        krakenExchangeClient = new KrakenExchangeClient(WebClient.create(), properties);
    }


    @Test
    void getCoinPrice() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(getJson("kraken-get-price.json")) // file in resources
        );

        String price = krakenExchangeClient.getPrice("BTC");

        assertThat(price).isEqualTo("52478.90000");

    }

    private String getJson(String path) {
        try {
            InputStream jsonStream = this.getClass().getClassLoader().getResourceAsStream(path);
            assert jsonStream != null;
            return new String(jsonStream.readAllBytes());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
