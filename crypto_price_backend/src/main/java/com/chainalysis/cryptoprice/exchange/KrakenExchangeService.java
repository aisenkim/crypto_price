package com.chainalysis.cryptoprice.exchange;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KrakenExchangeService implements ExchangeService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Getting price from recent 1000 trades
     */
    @Override
    public String getPrice(String coinSymbol) {
        URI price = ExchangeService.buildURI("https://api.kraken.com/0/public/Trades?pair=" + coinSymbol + "USD&since=" + new Timestamp(System.currentTimeMillis()));
        String result = restTemplate.getForObject(price, String.class);

        JSONObject resultObj = new JSONObject(result);

        JSONArray lastSoldPriceArr = resultObj.getJSONObject("result")
                .getJSONArray("X" + coinSymbol + "ZUSD")
                .getJSONArray(0);

        return lastSoldPriceArr.getString(0);
    }

    /**
     * Getting price from Ticker
     */
//    @Override
//    public String getPrice(String coinSymbol) {
//        URI price = ExchangeService.buildURI("https://api.kraken.com/0/public/Ticker?pair=" + coinSymbol + "USD");
//        String result = restTemplate.getForObject(price, String.class);
//
//        JSONObject resultObj = new JSONObject(result);
//
//        JSONArray lastSoldPriceArr = resultObj.getJSONObject("result")
//                .getJSONObject("X" + coinSymbol + "ZUSD")
//                .getJSONArray("c");
//
//        return lastSoldPriceArr.getString(0);
//    }

    @Override
    public Map<String, String> getFees(String coinSymbol) {
        Map<String, String> fees = new HashMap<>();
        URI krakenFeesUri = ExchangeService.buildURI("https://api.kraken.com/0/public/AssetPairs?pair=X" + coinSymbol + "ZUSD");
        String result = restTemplate.getForObject(krakenFeesUri, String.class);

        JSONObject feesObj = new JSONObject(result)
                .getJSONObject("result")
                .getJSONObject("X" + coinSymbol + "ZUSD");

        JSONArray takerFees = (JSONArray) feesObj
                .getJSONArray("fees")
                .get(0);
        JSONArray makerFees = (JSONArray) feesObj
                .getJSONArray("fees_maker")
                .get(0);

        String takerFeePercent = ExchangeService.convertPercentToDecimal(new BigDecimal(takerFees.get(1).toString()));
        String makerFeePercent = ExchangeService.convertPercentToDecimal(new BigDecimal(makerFees.get(1).toString()));

        fees.put("takerFees", takerFeePercent);
        fees.put("makerFees", makerFeePercent);

        return fees;
    }

    @Override
    public Map<String, String> getBuySellPrice(String coinSymbol) {
        // Kraken uses different symbol
        if (coinSymbol.equals("BTC")) {
            coinSymbol = "XBT";
        }

        BigDecimal price = new BigDecimal(getPrice(coinSymbol));
        Map<String, String> fees = getFees(coinSymbol);

        BigDecimal buyersFee = new BigDecimal(fees.get("takerFees"));
        BigDecimal sellersFee = new BigDecimal(fees.get("makerFees"));

        String buyPrice = ExchangeService.calculatePrice(price, buyersFee);
        String sellPrice = ExchangeService.calculatePrice(price, sellersFee);

        return Map.of(
                "buyPrice", buyPrice,
                "sellPrice", sellPrice
        );
    }


}
