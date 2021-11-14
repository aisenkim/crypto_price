package com.chainalysis.cryptoprice.exchange;

import com.chainalysis.cryptoprice.exchange.before_refactor.BlockchainExchangeService;
import com.chainalysis.cryptoprice.exchange.before_refactor.KrakenExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final KrakenExchangeClient krakenExchangeClient;
    private final BlockchainExchangeClient blockchainExchangeClient;


    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Map<String, String>> getBuySellPrice(
            @RequestParam String exchange,
            @RequestParam String coinSymbol
    ) {
        try {
            if (exchange.equals("kraken"))
                return ResponseEntity.ok().body(krakenExchangeClient.getBuySellPrice(coinSymbol));
            else
                return ResponseEntity.ok().body(blockchainExchangeClient.getBuySellPrice(coinSymbol));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    /**
     * USED FOR TESTING
     */
    @GetMapping("/getPrice")
    public ResponseEntity<String> getPrice(@RequestParam String coinSymbol) {
        return ResponseEntity.ok().body(blockchainExchangeClient.getPrice(coinSymbol));
    }

    /**
     * USED FOR TESTING
     */
    @GetMapping("/getFees")
    public ResponseEntity<Map<String, String>> getFees(@RequestParam String coinSymbol) {
        return ResponseEntity.ok().body(krakenExchangeClient.getFees(coinSymbol));
    }

}
