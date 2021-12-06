package com.redbrokers.exhange.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redbrokers.exhange.dto.ErrorFromExchange;
import com.redbrokers.exhange.dto.ErrorMessage;
import com.redbrokers.exhange.dto.FullOrderBook;
import com.redbrokers.exhange.dto.Order;
import com.redbrokers.exhange.service.ExchangeConnectivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeConnectivityServiceImpl implements ExchangeConnectivityService {
    @Value("${exchange.variables.exchange-url.order-url}")
    private String orderBaseUrl;
    @Value("${exchange.variables.exchange-url.one}")
    private String exchangeOneUrl;
    @Value("${exchange.variables.exchange-url.two}")
    private String exchangeTwoUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate;
    ErrorMessage.ErrorMessageBuilder errorMessage = ErrorMessage.builder();
    @Override
    public ResponseEntity<?> createOrder(Order order) {
        /**
         * Used java 9's HttpClient experimentally, for the api call. We can change this later
         */
        String orderToJson = null;
        try {
            orderToJson = objectMapper.writeValueAsString(order);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(exchangeOneUrl + orderBaseUrl))
                    .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .POST(HttpRequest.BodyPublishers.ofString(orderToJson))
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());


            if(response.statusCode() == HttpStatus.NOT_FOUND.value()) {
                return new ResponseEntity<>(ErrorMessage.builder()
                        .message("The exchange may not have this route")
                        .httpStatus(HttpStatus.NOT_FOUND).build(), HttpStatus.NOT_FOUND);
            }
            if(response.statusCode() == HttpStatus.BAD_REQUEST.value()) {
                return new ResponseEntity<>(objectMapper.readValue(response.body(), ErrorFromExchange.class),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if(response.statusCode() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                var ms = objectMapper.readValue(response.body(), ErrorFromExchange.class);
                return new ResponseEntity<>(ms, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(response.body(), HttpStatus.CREATED);
        } catch (JsonProcessingException ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(ex, HttpStatus.REQUEST_TIMEOUT);
        }  catch (URISyntaxException ex) {
           var error = ErrorMessage.builder()
                   .httpStatus(HttpStatus.BAD_REQUEST)
                   .message("Poorly formed Exchange URL. Confirm that the url is correct")
                   .build();

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } catch (InterruptedException | IOException ex) {
            var error = ErrorMessage.builder()
                    .httpStatus(HttpStatus.REQUEST_TIMEOUT)
                    .message("The request took too long to execute")
                    .build();
           return new ResponseEntity<>(error, HttpStatus.REQUEST_TIMEOUT);
        } catch (Exception ex) {
            var error = ErrorMessage.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("The exchange could not be reached")
                    .build();
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> checkOrderStatus(UUID orderId) {
        try {
            var url = exchangeOneUrl + orderBaseUrl+ orderId;
            var response = restTemplate.exchange(url, HttpMethod.GET, null, FullOrderBook.class);

            if(response.getStatusCode().is2xxSuccessful()) {
                return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
            }

            if(response.getStatusCodeValue() == HttpStatus.NOT_FOUND.value()) {
                var error = errorMessage.message("The Order with id" + orderId + " does not exist")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build();
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            if(response.getStatusCode().is5xxServerError()) {
                return  new ResponseEntity<>(objectMapper.convertValue(response.getBody(), ErrorFromExchange.class),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return null;
        } catch (Exception e) {
          return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

//        return new ResponseEntity<>();
    }

    @Override
    public ResponseEntity<?> cancelOrder(UUID orderId) {
        var url = exchangeOneUrl + orderBaseUrl+ orderId;

        return restTemplate.exchange(url, HttpMethod.DELETE, null, Boolean.class);
    }

    @Override
    public ResponseEntity<?> changeOrder(Order order, UUID orderId) {
        var url = exchangeOneUrl + orderBaseUrl+ orderId;
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(order, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Boolean.class);
    }
}
