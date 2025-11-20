package com.example.autenticacion.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroResponseDTO {
    private Boolean status;
    private String message;
    private Object archivo;

}