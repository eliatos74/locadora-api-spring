package com.elias.livraria_api.web.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(
        @NotBlank
        String username,
        @NotBlank
        @Size(min = 6, max = 6)
        String password
) {
}
