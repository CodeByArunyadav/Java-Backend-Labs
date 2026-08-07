package com.hoxcloud.lab.repository;

import com.hoxcloud.lab.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession,Long> {
    Optional<UserSession> findFirstByUserIdOrderByLastUsedAtAsc(String email);

    Long countByUserId(String email);

    boolean existsByRefreshToken(String refreshToken);
}
