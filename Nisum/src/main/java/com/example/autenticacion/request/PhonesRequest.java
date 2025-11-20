package com.example.autenticacion.request;

public record PhonesRequest(
        String number,
        String citycode,
        String contrycode
) {
}
