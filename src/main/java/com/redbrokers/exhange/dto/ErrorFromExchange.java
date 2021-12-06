package com.redbrokers.exhange.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = ErrorFromExchange.ErrorFromExchangeBuilder.class)
public class ErrorFromExchange {
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd'T'HH:mm:ss" )
    String timestamp;
    String status;
    String error;
    String message;
    String path;
    @JsonPOJOBuilder(withPrefix = "")
    public static class ErrorFromExchangeBuilder {}
}
