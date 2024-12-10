package com.elias.livraria_api.web.controller.dto;

public record UserUpdatePasswordDTO(String currentPassword, String newPassword, String confirmPassword) {

}
