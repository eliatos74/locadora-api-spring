package com.elias.livraria_api.web.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDTO(
        @NotBlank
        String username,
        @NotBlank
        @Email(message = "formato de e-mail esta invalido.", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        String email,
        @NotBlank
        @Size(min = 6, max = 6)
        String password,
        String role
) {
}
