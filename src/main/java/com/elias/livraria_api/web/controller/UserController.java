package com.elias.livraria_api.web.controller;


import com.elias.livraria_api.entity.User;
import com.elias.livraria_api.service.UserService;
import com.elias.livraria_api.web.controller.dto.UserCreateDTO;
import com.elias.livraria_api.web.controller.dto.UserResponseDTO;
import com.elias.livraria_api.web.controller.dto.UserUpdatePasswordDTO;
import com.elias.livraria_api.web.controller.dto.mapper.UserMapper;
import com.elias.livraria_api.web.controller.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "usuários", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = " Criar um novo usuário",
            description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Usuário ja cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Recurso não processado por dados de entradas invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO request) {
        System.out.println(request.toString());
        User user = userService.save(userMapper.toUser(request));
        var response = userMapper.toDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Recuperar um usuário pelo id",
            description = "Recuperar um usuário pelo id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getByUserId(@PathVariable Long id) {
        User user = userService.findId(id);
        var response = userMapper.toDTO(user);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Atualizar senha",
            description = "Atualizar senha",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Senha atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Senha não confere",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
                    )
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UserUpdatePasswordDTO request) {
        User user = userService.editPassword(id, request.currentPassword(), request.newPassword(), request.confirmPassword());
        var response = userMapper.toDTO(user);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os usuários", description = "Listar todos os usuários cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os usuários cadastrados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))))
            })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUser() {
        List<User> users = userService.findAll();
        var response = userMapper.toListDTO(users);
        return ResponseEntity.ok(response);
    }
}























