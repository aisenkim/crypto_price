package com.chainalysis.cryptoprice.exchange.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jayway.jsonpath.JsonPath;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.*;

@Data
public class KrakenGetPriceResponse {
//    @JsonAlias("result")
//    private Map<String, Object> result;

    @JsonAlias("error")
    private ArrayList<String> error;

    private String price;

    @JsonProperty("result")
    @SuppressWarnings("unchecked")
    private void resultDeserializer(Map<String, Object> result) {
        String coinSymbol = "";
        Optional<String> firstKey = result.keySet().stream().findFirst();
        if (firstKey.isPresent()) {
            coinSymbol = firstKey.get();
        }
        ArrayList<ArrayList<Object>> resultParsed = (ArrayList<ArrayList<Object>>) result.get(coinSymbol);
        ArrayList<Object> firstValue = resultParsed.get(0);
        this.price = (String) firstValue.get(0);
    }

}
