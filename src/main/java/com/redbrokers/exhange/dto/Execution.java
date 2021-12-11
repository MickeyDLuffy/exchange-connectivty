package com.redbrokers.exhange.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Execution {

   private String timestamp;
   private Double price;
   private int quantity;
}
