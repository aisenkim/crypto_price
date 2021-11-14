package com.chainalysis.cryptoprice.exchange;

import com.chainalysis.cryptoprice.utility.ExchangeUtility;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Map;

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
                        .setBody(ExchangeUtility.getJson("kraken-get-price.json", this.getClass())) // file in resources
        );

        String price = krakenExchangeClient.getPrice("BTC");

        assertThat(price).isEqualTo("52478.90000");

    }

    @Test
    void getFeePrice() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ExchangeUtility.getJson("kraken-get-fees.json", this.getClass()))
        );

        Map<String, String> fees = krakenExchangeClient.getFees("BTC");

        assertThat(fees.get("takerFees")).isEqualTo("0.0026");
        assertThat(fees.get("makerFees")).isEqualTo("0.0016");
    }

    @Test
    void getBuySellPrice() {
        // given
        String coinSymbol = "BTC";
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ExchangeUtility.getJson("kraken-get-price.json", this.getClass()))
        );
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ExchangeUtility.getJson("kraken-get-fees.json", this.getClass()))
        );

        Map<String, String> buySellPrice = krakenExchangeClient.getBuySellPrice(coinSymbol);

        assertThat(buySellPrice.get("buyPrice")).isEqualTo("52,615.35");
        assertThat(buySellPrice.get("sellPrice")).isEqualTo("52,562.87");

    }

}
