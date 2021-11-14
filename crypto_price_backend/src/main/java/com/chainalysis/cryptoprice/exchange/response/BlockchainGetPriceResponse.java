package com.chainalysis.cryptoprice.exchange.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Data
public class BlockchainGetPriceResponse {

    @JsonAlias("last_trade_price")
    private String price;


}
