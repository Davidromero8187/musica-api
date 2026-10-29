package com.musica;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/musica-api")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("¡Bienvenido a la API de Música!");
    }
}
  