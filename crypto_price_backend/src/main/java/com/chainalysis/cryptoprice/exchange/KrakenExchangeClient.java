package com.chainalysis.cryptoprice.exchange;

import com.chainalysis.cryptoprice.utility.ExchangeUtility;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class KrakenExchangeClient {

    private final WebClient webClient;
    private final KrakenExchangeProperties properties;

    public String getPrice(String coinSymbol) {
        String baseUrl = properties.getBaseUrl();
        URI priceURI = ExchangeUtility.buildURI(baseUrl + "/public/Trades?pair=" + coinSymbol + "USD&since=" + new Timestamp(System.currentTimeMillis()));
        return webClient.get()
                .uri(priceURI)
                .retrieve()
                .bodyToMono(KrakenExchangeResponse.class)
                .blockOptional()
                .map(response -> {
                    if (!response.getError().isEmpty()) {
                        throw new IllegalArgumentException();
                    } else {
//                        System.out.println("response.getPrice(coinSymbol) = " + response.getPrice());
                        return response.getPrice();
                    }
                })
                .orElseThrow(RuntimeException::new);
    }
}
