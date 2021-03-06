package com.chainalysis.cryptoprice.exchange.before_refactor;

import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.Map;

public interface ExchangeService {


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
