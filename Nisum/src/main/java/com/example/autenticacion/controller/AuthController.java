package com.example.autenticacion.controller;

import com.example.autenticacion.request.AuthRequest;
import com.example.autenticacion.request.RegisterRequest;
import com.example.autenticacion.response.RegistroUsuarioResponseDTO;
import com.example.autenticacion.response.TokenResponse;
import com.example.autenticacion.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<RegistroUsuarioResponseDTO> register(@Valid @RequestBody RegisterRequest request) {
        try {
            final RegistroUsuarioResponseDTO response = service.register(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)   // 201
                    .body(response);
        }catch (Exception e) {
            RegistroUsuarioResponseDTO error = new RegistroUsuarioResponseDTO();
            error.setMensaje(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)   // 201
                    .body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthRequest request) {
        final TokenResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication
    ) {
        return service.refreshToken(authentication);
    }


}
