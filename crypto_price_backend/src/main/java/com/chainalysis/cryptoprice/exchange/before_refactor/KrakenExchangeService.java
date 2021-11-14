package com.chainalysis.cryptoprice.exchange.before_refactor;

import com.chainalysis.cryptoprice.exchange.before_refactor.ExchangeService;
import com.chainalysis.cryptoprice.utility.ExchangeUtility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class KrakenExchangeService implements ExchangeService {

    //    @Autowired
    //    private RestTemplate restTemplate;
    private RestTemplate restTemplate;

    @Autowired
    public KrakenExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //    /**
//     * Getting price from recent 1000 trades
//     */
    @Override
    public String getPrice(String coinSymbol) {
        URI price = ExchangeUtility.buildURI("https://api.kraken.com/0/public/Trades?pair=" + coinSymbol + "USD&since=" + new Timestamp(System.currentTimeMillis()));
        String result = restTemplate.getForObject(price, String.class);

        JSONObject resultObj = new JSONObject(result);

        JSONArray errorArr = resultObj.getJSONArray("error");

        // check if error exist from api call
        if (!errorArr.isEmpty()) {
            System.out.println("Error Message: " + errorArr.get(0));
            throw new IllegalArgumentException(errorArr.get(0).toString());
        }

        JSONArray lastSoldPriceArr = resultObj.getJSONObject("result")
                .getJSONArray("X" + coinSymbol + "ZUSD")
                .getJSONArray(0);

        return lastSoldPriceArr.getString(0);
    }

    @Override
    public Map<String, String> getFees(String coinSymbol) {
        Map<String, String> fees = new HashMap<>();
        URI krakenFeesUri = ExchangeUtility.buildURI("https://api.kraken.com/0/public/AssetPairs?pair=X" + coinSymbol + "ZUSD");
        String result = restTemplate.getForObject(krakenFeesUri, String.class);

        JSONObject resultObj = new JSONObject(result);

        // check if error exist from api call
        JSONArray errorArr = resultObj.getJSONArray("error");
        if (!errorArr.isEmpty()) {
            System.out.println("Error Message: " + errorArr.get(0));
            throw new IllegalArgumentException(errorArr.get(0).toString());
        }

        JSONObject feesObj = resultObj
                .getJSONObject("result")
                .getJSONObject("X" + coinSymbol + "ZUSD");

        JSONArray takerFees = (JSONArray) feesObj
                .getJSONArray("fees")
                .get(0);
        JSONArray makerFees = (JSONArray) feesObj
                .getJSONArray("fees_maker")
                .get(0);

        String takerFeePercent =ExchangeUtility.convertPercentToDecimal(new BigDecimal(takerFees.get(1).toString()));
        String makerFeePercent =ExchangeUtility.convertPercentToDecimal(new BigDecimal(makerFees.get(1).toString()));

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

        String buyPrice = ExchangeUtility.calculatePrice(price, buyersFee);
        String sellPrice = ExchangeUtility.calculatePrice(price, sellersFee);

        return Map.of(
                "buyPrice", buyPrice,
                "sellPrice", sellPrice
        );
    }


}
