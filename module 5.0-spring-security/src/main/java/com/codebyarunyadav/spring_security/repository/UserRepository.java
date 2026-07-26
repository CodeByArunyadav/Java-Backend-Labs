package com.codebyarunyadav.spring_security.repository;

import com.codebyarunyadav.spring_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
Optional<User> findByEmail(String email);
}
