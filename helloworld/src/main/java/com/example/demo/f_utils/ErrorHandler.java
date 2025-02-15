package com.example.demo.f_utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

// locale
import org.springframework.context.MessageSource;
import java.util.Locale;

// logs
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(
        ErrorHandler.class
    );

    // locale
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(
        Exception error
    ) {

        try {

            // get error itens
            String errorMessage = error.getMessage();
            if (errorMessage == null || errorMessage.isEmpty()) {
                errorMessage = "Unknown error occurred";
            } else {
                errorMessage = errorMessage.substring(1, errorMessage.length() - 1);
            }

            Map<String, Object> errorMap = new LinkedHashMap<>();
            String[] keyValuePairs = errorMessage.split(", ");

            for (String line : keyValuePairs) {
                String[] KeyIten = line.split("=");
                errorMap.put(KeyIten[0], KeyIten[1]);
            }

            String errorCode = (String) errorMap.get("errorCode");
            String errorMessageDetail = (String) errorMap.get("message");

            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("statusCode", Integer.parseInt(errorCode));
            // field error
            if  (errorMap.get("field") != null) {
                String errorField = (String) errorMap.get("field");
                errorResponse.put("field", errorField);
            }
            errorResponse.put("message", errorMessageDetail);

            return ResponseEntity
            .status(Integer.parseInt(errorCode))
            .body(errorResponse);

        } catch (Exception e) {

            // locale
            Locale locale = LocaleContextHolder.getLocale();

            Map<String, Object> errorResponse = new LinkedHashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("statusCode", 500);
            errorResponse.put(
                "message",
                messageSource.getMessage(
                    "server_error", null, locale
                )
            );

            // logs
            logger.error(e.getMessage());

            return ResponseEntity
            .status(500)
            .body(errorResponse);
        }

    }

}
