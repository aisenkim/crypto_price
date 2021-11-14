package com.chainalysis.cryptoprice.utility;

import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.text.DecimalFormat;

/**
 * CONTAIN UTILITY METHODS FOR EXCHANGES
 */

public class ExchangeUtility {
    /**
     * Calculates price with fees combined
     *
     * @param price - featured coin price
     * @param fees  - adding a makers or takers fee
     * @return - total price in String format
     */
    public static String calculatePrice(BigDecimal price, BigDecimal fees) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        BigDecimal feePrice = price.multiply(fees);
        return decimalFormat.format(price.add(feePrice));
    }

    /**
     * Convert URL to URI
     *
     * @param url - API URL
     * @return API in URI format
     */
    public static URI buildURI(String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        return builder.build().encode().toUri();
    }


}
