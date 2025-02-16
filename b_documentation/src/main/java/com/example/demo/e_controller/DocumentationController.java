package com.example.demo.e_controller;

import com.example.demo.c_validation.DocumentationValidation;
import com.example.demo.d_services.DocumentationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping()
class DocumentationController {

    @Autowired
    private DocumentationService documentationService;

    @GetMapping("${BASE_URL_HELLOWORLD:default}/helloworld")
    public ResponseEntity handle(

        // validation errors
        @Valid DocumentationValidation documentationValidation,
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
                String field = (
                    (org.springframework.validation.FieldError) error
                ).getField();
                String messageError = error.getDefaultMessage();
                response.put("field", field);
                response.put("message", messageError);
            });

            throw new RuntimeException(response.toString());
        }

        return documentationService.execute(message);

    }

}