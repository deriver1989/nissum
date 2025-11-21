package com.example.autenticacion.request;

import com.example.autenticacion.validacion.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record RegisterRequest(
        String name,
        @NotBlank
        @Email(message = "El correo no tiene un formato válido")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
                message = "El correo debe tener formato local@dominio.ext (2-6 letras en la extensión)"
        )
        String email,
        @ValidPassword
        String password,
        String rol,
        List<PhonesRequest> phones
) {
}
