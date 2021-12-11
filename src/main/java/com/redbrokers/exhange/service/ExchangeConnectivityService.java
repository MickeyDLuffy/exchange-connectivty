package com.redbrokers.exhange.service;

import com.redbrokers.exhange.dto.FullOrderBook;
import com.redbrokers.exhange.dto.Order;
import com.redbrokers.exhange.enums.Exchange;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ExchangeConnectivityService {
    ResponseEntity<?> createOrder(Order order, Exchange exchange);
    ResponseEntity<?> checkOrderStatus(UUID orderId, Exchange exchange);
    ResponseEntity<?> cancelOrder(UUID orderId);
    ResponseEntity<?> changeOrder(Order order, UUID orderId);

}
