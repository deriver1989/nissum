package com.example.autenticacion.controller;

import com.example.autenticacion.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prueba")
@RequiredArgsConstructor
public class PruebaController {

    private final AuthService service;

    @GetMapping("/hola")
    public ResponseEntity<String> authenticate() {
        return ResponseEntity.ok("Hola Mundo");
    }

}
