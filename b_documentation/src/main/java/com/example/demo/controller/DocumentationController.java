package com.example.demo.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
class DocumentationController {

    @GetMapping(
        value = "/documentation/json",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> handle() {

        // Serve the OpenAPI JSON documentation
        ClassPathResource jsonFile = new ClassPathResource("static/documentation.json");

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(jsonFile);
    }

    @GetMapping("/documentation/swagger")
    public String getSwaggerUi() {
        return "<html>\n" +
            "<head>\n" +
            "<title>Swagger UI</title>\n" +
            "<script src='https://cdn.jsdelivr.net/npm/swagger-ui-dist@3.52.5/swagger-ui-bundle.js'></script>\n" +
            "<link rel='stylesheet' type='text/css' href='https://cdn.jsdelivr.net/npm/swagger-ui-dist@3.52.5/swagger-ui.css' />\n" +
            "<style>\n" +
            "  #swagger-ui {\n" +
            "    max-width: 80%;\n" +
            "    margin: 0 auto;\n" +
            "  }\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div id='swagger-ui'></div>\n" +
            "<script>\n" +
            "  const ui = SwaggerUIBundle({\n" +
            "    url: '/documentation/json',\n" +
            "    dom_id: '#swagger-ui',\n" +
            "    deepLinking: true,\n" +
            "    presets: [SwaggerUIBundle.presets.apis, SwaggerUIBundle.presets.sdk],\n" +
            "    layout: 'BaseLayout',\n" +
            "  });\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>";
    }

    @GetMapping("/documentation/redocly")
    public String getRedocUi() {
        return "<html>\n" +
            "<head>\n" +
            "<title>Redoc UI</title>\n" +
            "<script src='https://cdn.jsdelivr.net/npm/redoc@next/bundles/redoc.standalone.js'></script>\n" +
            "<style>\n" +
            "  #redoc-container {\n" +
            "    max-width: 100%;\n" +
            "    margin: 0 auto;\n" +
            "  }\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div id='redoc-container'></div>\n" +
            "<script>\n" +
            "  Redoc.init('/documentation/json', {\n" +
            "    theme: {\n" +
            "      typography: {\n" +
            "        fontSize: '12px', \n" +
            "        codeFontSize: '7px' \n" +
            "      }\n" +
            "    }\n" +
            "  }, document.getElementById('redoc-container'));\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>";
    }

}
