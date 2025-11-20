package com.example.autenticacion.request;

public record AuthRequest(
        String email,
        String password
) {
}
