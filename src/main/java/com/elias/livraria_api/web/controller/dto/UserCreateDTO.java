package com.elias.livraria_api.web.controller.dto;

public record UserCreateDTO(String name, String email, String password, String role) {
}
