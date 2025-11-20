package com.example.autenticacion.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroUsuarioResponseDTO {
    private UserResponse usuario;
    private String token;
    private Object archivo;
}