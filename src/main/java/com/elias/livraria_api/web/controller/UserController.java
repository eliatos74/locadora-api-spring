package com.elias.livraria_api.web.controller;


import com.elias.livraria_api.entity.User;
import com.elias.livraria_api.service.UserService;
import com.elias.livraria_api.web.controller.dto.UserCreateDTO;
import com.elias.livraria_api.web.controller.dto.UserResponseDTO;
import com.elias.livraria_api.web.controller.dto.UserUpdatePasswordDTO;
import com.elias.livraria_api.web.controller.dto.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO request) {
        System.out.println(request.toString());
        User user = userService.save(userMapper.toUser(request));
        var response = userMapper.toDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getByUserId(@PathVariable Long id) {
        User user = userService.findId(id);
        var response = userMapper.toDTO(user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UserUpdatePasswordDTO request) {
        User user = userService.editPassword(id, request.currentPassword(), request.newPassword(), request.confirmPassword());
        var response =  userMapper.toDTO(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUser() {
        List<User> users = userService.findAll();
        var response  =  userMapper.toListDTO(users);
        return ResponseEntity.ok(response);
    }
}























