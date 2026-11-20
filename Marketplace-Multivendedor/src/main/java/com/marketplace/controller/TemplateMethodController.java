
package com.marketplace.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marketplace.service.templatemethod.TemplateMethodService;

@RestController
@RequestMapping("/api/template-method")
@CrossOrigin
public class TemplateMethodController {

    private final TemplateMethodService templateMethodService;

    public TemplateMethodController(
            TemplateMethodService templateMethodService) {
        this.templateMethodService = templateMethodService;
    }

    @GetMapping("/publicar")
    public ResponseEntity<String> publicarProducto(
            @RequestParam String tipo) {

        String resultado =
                templateMethodService.publicarProducto(tipo);

        return ResponseEntity.ok(resultado);
    }
}
