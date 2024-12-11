package com.elias.livraria_api.web.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdatePasswordDTO(
        @NotBlank
        @Size(min = 6, max = 6)
        String currentPassword,
        @NotBlank
        @Size(min = 6, max = 6)
        String newPassword,
        @NotBlank
        @Size(min = 6, max = 6)
        String confirmPassword
) {
}
