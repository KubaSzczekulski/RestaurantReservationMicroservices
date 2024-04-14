package com.example.demo.repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmailAndUserIDNot(String email, String id);
    Optional<UserEntity> findByTelephoneNumberAndUserIDNot(String email, String id);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByTelephoneNumber(String email);
}
