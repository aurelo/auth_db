package com.kanezi.authdb.security.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findUserEntityByEmail(String email);
}
