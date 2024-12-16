package com.elias.livraria_api.web.controller;

import com.elias.livraria_api.jwt.JwtToken;
import com.elias.livraria_api.jwt.JwtUserDetailsService;
import com.elias.livraria_api.web.controller.dto.UserLoginDTO;
import com.elias.livraria_api.web.controller.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UserLoginDTO dto, HttpServletRequest request) {
        log.info("Processo de autenticação pelo login {}", dto.username());
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());

            authenticationManager.authenticate(authenticationToken);

            JwtToken token = detailsService.getTokenAuthenticated(dto.username());

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.warn("Bad Credentials from username '{}'", dto.username());
        }
        return ResponseEntity
                .badRequest()
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
    }
}
