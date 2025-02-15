package com.example.demo.e_controller;

import com.example.demo.c_validation.HelloWorldValidation;
import com.example.demo.f_utils.StandardResponse;
import com.example.demo.d_services.HelloWorldService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.MessageSource;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping()
class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    // language
    private final MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("${BASE_URL_HELLOWORLD:default}/helloworld")
    public StandardResponse handle(

        // validation errors
        @Valid HelloWorldValidation helloWorldValidation,
        BindingResult bindingResult,

        @RequestParam(
            value = "message", defaultValue = "Hello World!"
        ) String message

    ) {

        // get language
        Locale locale = LocaleContextHolder.getLocale();

        // return validation errors
        if (bindingResult.hasErrors()) {

            // field error response
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("errorCode", 400);
            bindingResult.getAllErrors().forEach(error -> {
                String field = ((org.springframework.validation.FieldError) error).getField();
                String messageError = error.getDefaultMessage();
                response.put("field", field);
                response.put("message", messageError);
            });

            throw new RuntimeException(response.toString());
        }

        return helloWorldService.execute(message);

    }

}