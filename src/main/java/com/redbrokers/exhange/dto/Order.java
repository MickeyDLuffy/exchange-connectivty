package com.redbrokers.exhange.dto;

import com.redbrokers.exhange.enums.Ticker;
import lombok.Data;

@Data
public class Order {
    private int quantity;
    private Double price;
    private Ticker product;
    private Side side;

    @Override
    public String toString() {
        return "Order{" +
                "quantity=" + quantity +
                ", price=" + price +
                ", side=" + side +
                ", product=" + product +
                '}';
    }
}
