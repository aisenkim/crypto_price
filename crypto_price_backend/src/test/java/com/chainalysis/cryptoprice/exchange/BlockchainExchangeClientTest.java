package com.chainalysis.cryptoprice.exchange;

import com.chainalysis.cryptoprice.exchange.properties.BlockchainExchangeProperties;
import com.chainalysis.cryptoprice.exchange.properties.KrakenExchangeProperties;
import com.chainalysis.cryptoprice.utility.ExchangeUtility;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BlockchainExchangeClientTest {
    private MockWebServer mockWebServer;
    private BlockchainExchangeClient blockchainExchangeClient;

    @BeforeEach
    void setupMockWebServer() {
        mockWebServer = new MockWebServer();

        BlockchainExchangeProperties properties = new BlockchainExchangeProperties();
        properties.setBaseUrl(mockWebServer.url("/").url().toString());
        blockchainExchangeClient = new BlockchainExchangeClient(WebClient.create(), properties);
    }


    @Test
    void getCoinPrice() {
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ExchangeUtility.getJson("blockchain-get-price.json", this.getClass())) // file in resources
        );

        String price = blockchainExchangeClient.getPrice("BTC");

        assertThat(price).isEqualTo("64714.7");

    }

    @Test
    void getBuySellPrice() {
        // given
        String coinSymbol = "BTC";
        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(ExchangeUtility.getJson("blockchain-get-price.json", this.getClass()))
        );

        Map<String, String> buySellPrice = blockchainExchangeClient.getBuySellPrice(coinSymbol);

        assertThat(buySellPrice.get("buyPrice")).isEqualTo("64,870.02");
        assertThat(buySellPrice.get("sellPrice")).isEqualTo("64,805.30");

    }

}
