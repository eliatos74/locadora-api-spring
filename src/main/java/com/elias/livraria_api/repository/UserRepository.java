package com.elias.livraria_api.repository;

import com.elias.livraria_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
