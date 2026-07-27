package com.codebyarunyadav.spring_security.repository;

import com.codebyarunyadav.spring_security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
Optional<UserEntity> findByEmail(String email);
}
