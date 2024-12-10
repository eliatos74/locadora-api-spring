package com.elias.livraria_api.service;


import com.elias.livraria_api.entity.User;
import com.elias.livraria_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User save(User userRequest) {
        return userRepository.save(userRequest);
    }

    @Transactional(readOnly = true)
    public User findId(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado.")
        );
    }

    @Transactional
    public User editPassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Nova senha não confere com confirmação de senha.");
        }
        User user = findId(id);
        if (!user.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Sua senha não confere.");
        }
        user.setPassword(newPassword);
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
