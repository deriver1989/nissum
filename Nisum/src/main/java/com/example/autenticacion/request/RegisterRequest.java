package com.example.autenticacion.request;

import java.util.List;

public record RegisterRequest(
        String name,
        String email,
        String password,
        String rol,
        List<PhonesRequest> phones
) {
}
