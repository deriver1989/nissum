package com.example.autenticacion.request;

public record RegisterRequest(
        String name,
        String email,
        String password,
        String rol
) {
}
