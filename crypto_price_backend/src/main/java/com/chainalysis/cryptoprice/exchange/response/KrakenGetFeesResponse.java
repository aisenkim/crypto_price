package com.chainalysis.cryptoprice.exchange.response;

import com.chainalysis.cryptoprice.utility.ExchangeUtility;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
public class KrakenGetFeesResponse {
    @JsonAlias("error")
    private ArrayList<String> error;

    private Map<String, String> fees;

    @JsonProperty("result")
    @SuppressWarnings("unchecked")
    private void resultDeserializer(Map<String, Object> result) {
        String coinSymbol = "";

        // GET COIN SYMBOL FROM RESULT
        Optional<String> firstKey = result.keySet().stream().findFirst();
        if (firstKey.isPresent()) {
            coinSymbol = firstKey.get();
        }

        Map<String, Object> resultParsed = (Map<String, Object>) result.get(coinSymbol);
        List<List<Number>> fees = (List<List<Number>>) resultParsed.get("fees");
        List<List<Number>> fees_maker = (List<List<Number>>) resultParsed.get("fees_maker");

        // GRAB PERCENTAGE OF A FEE
        Double takerFees = (Double) fees.get(0).get(1);
        Double makerFees = (Double) fees_maker.get(0).get(1);

        // CONVERT PERCENT TO DECIMAL FOR CALCULATION
        String takerFeePercent = ExchangeUtility.convertPercentToDecimal(new BigDecimal(takerFees.toString()));
        String makerFeePercent = ExchangeUtility.convertPercentToDecimal(new BigDecimal(makerFees.toString()));

        this.fees = Map.of("takerFees", takerFeePercent, "makerFees", makerFeePercent);
    }
}
