package com.elias.livraria_api.service;


import com.elias.livraria_api.entity.User;
import com.elias.livraria_api.exception.EmailUniqueViolationException;
import com.elias.livraria_api.exception.EntityNotFoundException;
import com.elias.livraria_api.exception.PasswordInvalidException;
import com.elias.livraria_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User userRequest) {
        try {
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            return userRepository.save(userRequest);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new EmailUniqueViolationException(String.format("Email %s ja existe.", userRequest.getEmail()));
        }
    }

    @Transactional(readOnly = true)
    public User findId(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com id = %s não encontrado.", id))
        );
    }

    @Transactional
    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }
        User user = findId(id);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordInvalidException("Sua senha não confere.");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com %s não encontrado.", username))
        );
    }

    @Transactional(readOnly = true)
    public User.Role findRoleUsername(String username) {
        return userRepository.findRoleByUsername(username);
    }
}
