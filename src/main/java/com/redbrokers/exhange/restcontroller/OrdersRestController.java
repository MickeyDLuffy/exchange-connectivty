package com.redbrokers.exhange.restcontroller;

import com.redbrokers.exhange.dto.Order;
import com.redbrokers.exhange.service.ExchangeConnectivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchange/orders")
public class OrdersRestController {
    private final ExchangeConnectivityService exchangeService;
    /***
     * @param order The order being placed
     * @return Returns the id of the order created or an error response object
     *
     */
    @PostMapping("/create-order")
    public ResponseEntity<?> makeOrder(@RequestBody Order order) {
       return exchangeService.createOrder(order);
    }

    /**
     *
     * @param orderId the id of the order whose status is required
     * @return a FullOrderBook object
     * @apiNote Orders can be partially executed, hence contains a list of
     * executions which should sum up to the total order made
     */
    @GetMapping("/{orderId}" )
    public ResponseEntity<?> checkOrderStatus(@PathVariable UUID orderId) {
      return exchangeService.checkOrderStatus(orderId);
    }

    /**
     * @param orderId the id of the order whose status is required
     * @return a boolean true if successful, or a ErrorMessage response object detailing the error
     * @apiNote If the order has already been partially filled, only the component of the order
     * that has not yet been executed is cancelled
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable UUID orderId) {
        return exchangeService.cancelOrder(orderId);
    }

    /**
     * @param order the changes to be made to the order
     * @param orderId id of the order to be changed
     * @return true if the order is updated else a ErrorMessage response object detailing the error
     * @apiNote Only the quantity or price can be updated. SIDE and PRODUCT must match the
     * original order. Partially filled orders would have the quantity/price part filled
     */
    @PutMapping("/{orderId}")
    public ResponseEntity<?> changeOrderDetails(@RequestBody Order order, @PathVariable UUID orderId) {
        return exchangeService.changeOrder(order, orderId);
    }
}
