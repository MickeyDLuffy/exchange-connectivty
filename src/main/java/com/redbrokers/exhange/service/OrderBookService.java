package com.redbrokers.exhange.service;

import com.redbrokers.exhange.dto.Order;
import com.redbrokers.exhange.enums.Exchange;
import com.redbrokers.exhange.enums.Ticker;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderBookService {
    ResponseEntity<?> getOrderBooks(Exchange exchange);
    ResponseEntity<?> getOrderBooksForTicker(Exchange exchange, Ticker ticker);
    ResponseEntity<?> getBuyOrderBooksForTicker(Exchange exchange, Ticker ticker);
    ResponseEntity<?> getSellOrderBooksForTicker(Exchange exchange, Ticker ticker);
    ResponseEntity<?> getOpenOrderBooksForTicker(Exchange exchange, Ticker ticker);
    ResponseEntity<?> getClosedOrderBooksForTicker(Exchange exchange, Ticker ticker);
    ResponseEntity<?> getCancelledOrderBooksForTicker(Exchange exchange, Ticker ticker);

}
