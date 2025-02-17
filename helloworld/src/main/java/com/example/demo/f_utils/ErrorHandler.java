package com.example.demo.f_utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public ResponseEntity handleAllExceptions(
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

            // field error (form)
            String errorField = "";
            if  (errorMap.get("field") != null) {
                errorField = (String) errorMap.get("field");
            }

            StandardResponse response = new StandardResponse.Builder()
                .statusCode(Integer.parseInt(errorCode))
                .statusMessage("error")
                .field(errorField)
                .message(errorMessageDetail)
                .build();

            return ResponseEntity
            .status(response.getStatusCode())
            .body(response);

        } catch (Exception e) {

            // locale
            Locale locale = LocaleContextHolder.getLocale();

            // logs
            logger.error(error.toString());

            StandardResponse response = new StandardResponse.Builder()
                .statusCode(500)
                .statusMessage("error")
                .message(
                    messageSource.getMessage(
                    "server_error", null, locale
                    )
                )
                .build();

            return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
        }

    }

}