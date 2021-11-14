package com.chainalysis.cryptoprice.exchange;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final KrakenExchangeClient krakenExchangeClient;
    private final KrakenExchangeService krakenExchangeService;
    private final BlockchainExchangeService blockchainExchangeService;


    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Map<String, String>> getBuySellPrice(
            @RequestParam String exchange,
            @RequestParam String coinSymbol
    ) {
        try {
            if (exchange.equals("kraken"))
                return ResponseEntity.ok().body(krakenExchangeService.getBuySellPrice(coinSymbol));
            else
                return ResponseEntity.ok().body(blockchainExchangeService.getBuySellPrice(coinSymbol));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/getPrice")
    public ResponseEntity<String> getPrice(@RequestParam String coinSymbol) {
        return ResponseEntity.ok().body(krakenExchangeClient.getPrice(coinSymbol));
    }

}
