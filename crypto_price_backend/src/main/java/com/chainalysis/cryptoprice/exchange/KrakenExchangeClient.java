package com.chainalysis.cryptoprice.exchange;

import com.chainalysis.cryptoprice.exchange.response.KrakenGetFeesResponse;
import com.chainalysis.cryptoprice.exchange.response.KrakenGetPriceResponse;
import com.chainalysis.cryptoprice.utility.ExchangeUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.net.URI;
import java.sql.Timestamp;
import java.util.Map;

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
                .bodyToMono(KrakenGetPriceResponse.class)
                .blockOptional()
                .map(response -> {
                    if (!response.getError().isEmpty()) {
                        throw new IllegalArgumentException();
                    } else {
                        return response.getPrice();
                    }
                })
                .orElseThrow(RuntimeException::new);
    }

    public Map<String, String> getFees(String coinSymbol) {
        String baseUrl = properties.getBaseUrl();
        URI feeURI = ExchangeUtility.buildURI(baseUrl + "/public/AssetPairs?pair=X" + coinSymbol + "ZUSD");
        return webClient.get()
                .uri(feeURI)
                .retrieve()
                .bodyToMono(KrakenGetFeesResponse.class)
                .blockOptional()
                .map(response -> {
                    if (!response.getError().isEmpty()) {
                        throw new IllegalArgumentException();
                    } else {
                        return response.getFees();
                    }
                })
                .orElseThrow(RuntimeException::new);
    }

    public Map<String, String> getBuySellPrice(String coinSymbol) {
        if (coinSymbol.equals("BTC")) {
            coinSymbol = "XBT";
        }

        BigDecimal price = new BigDecimal(getPrice(coinSymbol));
        Map<String, String> fees = getFees(coinSymbol);

        BigDecimal buyersFee = new BigDecimal(fees.get("takerFees"));
        BigDecimal sellersFee = new BigDecimal(fees.get("makerFees"));

        String buyPrice = ExchangeUtility.calculatePrice(price, buyersFee);
        String sellPrice = ExchangeUtility.calculatePrice(price, sellersFee);

        return Map.of(
                "buyPrice", buyPrice,
                "sellPrice", sellPrice
        );
    }
}
