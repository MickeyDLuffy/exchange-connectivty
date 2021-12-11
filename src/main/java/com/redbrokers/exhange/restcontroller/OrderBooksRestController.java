package com.redbrokers.exhange.restcontroller;

import com.redbrokers.exhange.enums.Exchange;
import com.redbrokers.exhange.enums.Ticker;
import com.redbrokers.exhange.service.OrderBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/{API_URL}/order-books")
@RequiredArgsConstructor
public class OrderBooksRestController {
    private final OrderBookService orderBookService;

    @GetMapping("/")
    public ResponseEntity<?> getOrderBooks(@PathVariable UUID API_URL,
                                           @RequestParam Exchange exchange) {
        return orderBookService.getOrderBooks(exchange);
    }
    @GetMapping("/{ticker}")
    public ResponseEntity<?> getOrderBooksForTicker(@PathVariable Ticker ticker,
                                                    @PathVariable UUID API_URL,
                                                    @RequestParam Exchange exchange) {
         return orderBookService.getOrderBooksForTicker(exchange, ticker);
    }

    @GetMapping("/{ticker}/buy")
    public ResponseEntity<?> getBuyOrderBooksForTicker(@PathVariable Ticker ticker,
                                                       @PathVariable UUID API_URL,
                                                       @RequestParam Exchange exchange) {
      return orderBookService.getBuyOrderBooksForTicker(exchange, ticker);
    }

    @GetMapping("/{ticker}/sell")
    public ResponseEntity<?> getSellOrderBooksForTicker(@PathVariable Ticker ticker,
                                                        @PathVariable UUID API_URL,
                                                        @RequestParam Exchange exchange) {
       return orderBookService.getSellOrderBooksForTicker(exchange, ticker);
    }

    @GetMapping("/{ticker}/open")
    public ResponseEntity<?> getOpenOrderBooksForTicker(@PathVariable Ticker ticker,
                                                    @PathVariable UUID API_URL,
                                                    @RequestParam Exchange exchange) {
       return orderBookService.getOpenOrderBooksForTicker(exchange, ticker);
    }

    @GetMapping("/{ticker}/closed")
    public ResponseEntity<?> getClosedOrderBooksForTicker(@PathVariable Ticker ticker,
                                                    @PathVariable UUID API_URL,
                                                    @RequestParam Exchange exchange) {
        return orderBookService.getClosedOrderBooksForTicker(exchange, ticker);
    }

    @GetMapping("/{ticker}/cancelled")
    public ResponseEntity<?> getCancelledOrderBooksForTicker(@PathVariable Ticker ticker,
                                                             @PathVariable UUID API_URL,
                                                             @RequestParam Exchange exchange) {
       return orderBookService.getCancelledOrderBooksForTicker(exchange, ticker);
    }



}
