package com.redbrokers.exhange.service;

import com.redbrokers.exhange.dto.ErrorMessage;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ApiKeyValidationService {
    ResponseEntity<ErrorMessage> onKeyInvalid();
    boolean isValid(UUID API_KEY);
}
