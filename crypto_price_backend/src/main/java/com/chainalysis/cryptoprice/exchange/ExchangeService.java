package com.chainalysis.cryptoprice.exchange;

import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.Map;

public interface ExchangeService {
    /**
     * Convert URL to URI
     *
     * @param url - API URL
     * @return API in URI format
     */
    static URI buildURI(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        return builder.build().encode().toUri();
    }

    /**
     * Calculates price with fees combined
     *
     * @param price - featured coin price
     * @param fees  - adding a makers or takers fee
     * @return - total price in String format
     */
    static String calculatePrice(BigDecimal price, BigDecimal fees) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        BigDecimal feePrice = price.multiply(fees);
        return decimalFormat.format(price.add(feePrice));
    }

    static String convertPercentToDecimal(BigDecimal fees) {
        return fees.divide(new BigDecimal("100") ).toString();
    }

    /**
     * API call to retrieve last trade closed coin value
     *
     * @param coinSymbol - (BTC, ETH)
     * @return - price
     */
    String getPrice(String coinSymbol);

    /**
     * Calls API to retrieve maker fee and taker fee
     *
     * @param coinSymbol - (BTC, ETH)
     * @return - mapped as {takerFee : "value", makerFee: "value"}
     */
    Map<String, String> getFees(String coinSymbol);

    /**
     * Returns buyer price and seller price in string format
     */
    Map<String, String> getBuySellPrice(String coinSymbol);

}
