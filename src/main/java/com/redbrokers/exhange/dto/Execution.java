package com.redbrokers.exhange.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Execution {

   private LocalDateTime timestamp;
   private Double price;
   private int quantity;
}
