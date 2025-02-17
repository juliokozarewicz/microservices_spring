package com.example.demo.e_controller;

import com.example.demo.c_validation.HelloWorldValidation;
import com.example.demo.d_service.HelloWorldService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping()
class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping("${BASE_URL_HELLOWORLD:default}/helloworld")
    public ResponseEntity handle(

        // validation errors
        @Valid HelloWorldValidation helloWorldValidation,
        BindingResult bindingResult,

        @RequestParam(
            value = "message", defaultValue = "Hello World!"
        ) String message

    ) {

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

        return helloWorldService.execute(message);

    }

}