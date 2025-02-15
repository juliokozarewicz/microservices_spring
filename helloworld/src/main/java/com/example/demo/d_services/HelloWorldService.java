package com.example.demo.d_services;

import com.example.demo.f_utils.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

// locale
import org.springframework.context.MessageSource;
import java.util.Locale;

@Service
public class HelloWorldService {

    // locale
    @Autowired
    private MessageSource messageSource;

    public StandardResponse execute(
        String message
    ) {

        // language
        Locale locale = LocaleContextHolder.getLocale();

        // response (json)
        return new StandardResponse.Builder()
            .statusCode(200)
            .statusMessage(
                messageSource.getMessage(
            "get_data_success", null, locale
                )
            )
            .message(message)
            .build();

    }

}