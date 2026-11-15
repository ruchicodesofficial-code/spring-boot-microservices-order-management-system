package com.ruchicodes.userservice.repository;

import com.ruchicodes.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email,Long id);
}
