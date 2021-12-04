package com.redbrokers.exhange.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;


@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = ErrorMessage.ErrorMessageBuilder.class)
public class ErrorMessage {
    String message;
    HttpStatus httpStatus;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ErrorMessageBuilder {}
}
