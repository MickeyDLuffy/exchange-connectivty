package com.redbrokers.exhange.service;

import com.redbrokers.exhange.dto.FullOrderBook;
import com.redbrokers.exhange.dto.Order;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ExchangeConnectivityService {
    ResponseEntity<?> createOrder(Order order);
    ResponseEntity<?> checkOrderStatus(UUID orderId);
    ResponseEntity<?> cancelOrder(UUID orderId);
    ResponseEntity<?> changeOrder(Order order, UUID orderId);

}
