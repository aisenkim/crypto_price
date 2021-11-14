package com.chainalysis.cryptoprice.exchange;

import com.chainalysis.cryptoprice.exchange.properties.BlockchainExchangeProperties;
import com.chainalysis.cryptoprice.exchange.response.BlockchainGetPriceResponse;
import com.chainalysis.cryptoprice.utility.ExchangeUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class BlockchainExchangeClient {

    private final WebClient webClient;
    private final BlockchainExchangeProperties properties;

    public String getPrice(String coinSymbol) {
        String baseUrl = properties.getBaseUrl();
        URI priceURI = ExchangeUtility.buildURI(baseUrl + "/exchange/tickers/" + coinSymbol + "-USD");
        return webClient.get()
                .uri(priceURI)
                .retrieve()
                .bodyToMono(BlockchainGetPriceResponse.class)
                .blockOptional()
                .map(BlockchainGetPriceResponse::getPrice)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * NOTE - Used fixed rate from website due to api keys
     */
    public Map<String, String> getFees(String coinSymbol) {
        return Map.of(
                "takerFees", "0.0024",
                "makerFees", "0.0014"
        );
    }

    public Map<String, String> getBuySellPrice(String coinSymbol) {
        BigDecimal price = new BigDecimal(getPrice(coinSymbol));
        Map<String, String> fees = getFees(coinSymbol);

        BigDecimal buyersFee = new BigDecimal(fees.get("takerFees"));
        BigDecimal sellersFee = new BigDecimal(fees.get("makerFees"));

        String buyPrice = ExchangeUtility.calculatePrice(price, buyersFee);
        String sellPrice =  ExchangeUtility.calculatePrice(price, sellersFee);

        return Map.of(
                "buyPrice", buyPrice,
                "sellPrice", sellPrice
        );
    }
}
