package com.redbrokers.exhange.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbrokers.exhange.dto.AllFullOrderBook;
import com.redbrokers.exhange.dto.FullOrderBook;
import com.redbrokers.exhange.enums.Exchange;
import com.redbrokers.exhange.enums.Ticker;
import com.redbrokers.exhange.service.OrderBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderBookServiceImpl implements OrderBookService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    @Value("${exchange.variables.exchange-url.one}")
    private String exchangeOneUrl;
    @Value("${exchange.variables.exchange-url.two}")
    private String exchangeTwoUrl;

    /**
     * @apiNote Dont use this, rather use the more refined versions below. This returns a lot of data and
     * is really slow
     * @param exchange Whether the first exchange or the second
     * @return All order books for all products , whether BUY or SELL, OPEN or CLOSED
     */
    @Override
    public ResponseEntity<?> getOrderBooks(Exchange exchange) {
        var response = restTemplate.exchange(getExchangeUrl(exchange),
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<AllFullOrderBook>>() {
                });

        return null;
    }

    /**
     *
     * @param exchange Whether the first exchange or the second
     * @param ticker The product whose order book you need i.e IBM
     * @return List of order books for the given ticker from provided exchange both BUY and SELL
     */
    @Override
    public ResponseEntity<?> getOrderBooksForTicker(Exchange exchange, Ticker ticker) {
        var url = UriComponentsBuilder.fromUriString(getExchangeUrl(exchange))
                .path(ticker.name())
                .build()
                .toString();
        var response = restTemplate.exchange(url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<FullOrderBook>>() {
                });
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    /**
     *
     * @param exchange Whether the first exchange or the second
     * @param ticker The product whose order book you need i.e IBM
     * @return List of BUY order books for the given ticker from provided exchange
     */
    @Override
    public ResponseEntity<?> getBuyOrderBooksForTicker(Exchange exchange, Ticker ticker) {
        var url = UriComponentsBuilder.fromUriString(getExchangeUrl(exchange))
                .path(ticker.name())
                .path("/buy")
                .build()
                .toString();
        var response = restTemplate.exchange(url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<FullOrderBook>>() {
                });
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    /**
     *
     * @param exchange Whether the first exchange or the second
     * @param ticker The product whose order book you need i.e IBM
     * @return List of SELL order books for the given ticker from provided exchange
     */
    @Override
    public ResponseEntity<?> getSellOrderBooksForTicker(Exchange exchange, Ticker ticker) {
        var url = UriComponentsBuilder.fromUriString(getExchangeUrl(exchange))
                .path(ticker.name())
                .path("/sell")
                .build()
                .toString();
        var response = restTemplate.exchange(url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<FullOrderBook>>() {
                });
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    /**
     *
     * @param exchange Whether the first exchange or the second
     * @param ticker The product whose order book you need i.e IBM
     * @return List of OPEN order books for the given ticker from provided exchange
     */
    @Override
    public ResponseEntity<?> getOpenOrderBooksForTicker(Exchange exchange, Ticker ticker) {
        var url = UriComponentsBuilder.fromUriString(getExchangeUrl(exchange))
                .path(ticker.name())
                .path("/open")
                .build()
                .toString();
        var response = restTemplate.exchange(url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<FullOrderBook>>() {
                });
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    /**
     *
     * @param exchange Whether the first exchange or the second
     * @param ticker The product whose order book you need i.e IBM
     * @return List of CLOSED order books for the given ticker from provided exchange
     */
    @Override
    public ResponseEntity<?> getClosedOrderBooksForTicker(Exchange exchange, Ticker ticker) {
        var url = UriComponentsBuilder.fromUriString(getExchangeUrl(exchange))
                .path(ticker.name())
                .path("/closed")
                .build()
                .toString();
        var response = restTemplate.exchange(url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<FullOrderBook>>() {
                });
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    /**
     *
     * @param exchange Whether the first exchange or the second
     * @param ticker The product whose order book you need i.e IBM
     * @return List of CANCELLED order books for the given ticker from provided exchange
     */
    @Override
    public ResponseEntity<?> getCancelledOrderBooksForTicker(Exchange exchange, Ticker ticker) {
        var url = UriComponentsBuilder.fromUriString(getExchangeUrl(exchange))
                .path(ticker.name())
                .path("/cancelled")
                .build()
                .toString();
        var response = restTemplate.exchange(url,
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<FullOrderBook>>() {
                });
        return new ResponseEntity<>(response.getBody(), response.getStatusCode());
    }

    private String getExchangeUrl(Exchange exchange) {
        return   exchange.equals(Exchange.ONE) ? exchangeOneUrl + "orderbook/" : exchangeTwoUrl + "orderbook/";
    }
}
