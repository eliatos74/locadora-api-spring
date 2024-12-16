package com.elias.livraria_api.web.controller.dto.mapper;

import com.elias.livraria_api.entity.User;
import com.elias.livraria_api.web.controller.dto.UserCreateDTO;
import com.elias.livraria_api.web.controller.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toUser(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setUsername(userCreateDTO.username());
        user.setEmail(userCreateDTO.email());
        user.setPassword(userCreateDTO.password());
        user.setRole(User.Role.valueOf(userCreateDTO.role()));
        return user;
    }

    public UserResponseDTO toDTO(User user) {
        var role = user.getRole().name().substring("ROLE_".length());
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(), role);
    }

    public List<UserResponseDTO> toListDTO(List<User> users) {
        return users.stream().map(user -> toDTO(user)).collect(Collectors.toList());
    }
}
