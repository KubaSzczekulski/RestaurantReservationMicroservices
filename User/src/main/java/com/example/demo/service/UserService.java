package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserError;
import com.example.demo.exception.UserException;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


    public UserEntity getUserById(String userID) {
        return userRepository.findById(userID)
                .orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }

    public boolean areAllFieldsSet(String userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return false;
        }
        UserEntity user = userOptional.get();
        return Stream.of(user.getFirstName(), user.getEmail(), user.getLastName(), user.getTelephoneNumber() )
                .noneMatch(Objects::isNull);
    }
    @Transactional
    public UserEntity addUser(UserEntity userEntity, String userId) {
        if (userIdExists(userId)) {
            throw new UserException(UserError.USER_ALREADY_EXISTS);
        }
        if (emailExists(userEntity.getEmail())) {
            throw new UserException(UserError.EMAIL_ALREADY_EXISTS);
        }
        if (telephoneNumberExists(userEntity.getTelephoneNumber())) {
            throw new UserException(UserError.TELEPHONE_ALREADY_EXISTS);
        }
        userEntity.setUserID(userId);
        return userRepository.save(userEntity);
    }

    @Transactional
    public UserEntity updateUser(String userID, UserEntity userEntity) {
        return userRepository.findById(userID)
                .map(userExisting -> {

                    if (emailExistsExcludeId(userEntity.getEmail(), userID)) {
                        throw new UserException(UserError.EMAIL_ALREADY_EXISTS);
                    }
                    if (telephoneNumberExistsExcludeId(userEntity.getTelephoneNumber(), userID)) {
                        throw new UserException(UserError.TELEPHONE_ALREADY_EXISTS);
                    }
                    userExisting.setFirstName(userEntity.getFirstName());
                    userExisting.setLastName(userEntity.getLastName());
                    userExisting.setTelephoneNumber(userEntity.getTelephoneNumber());
                    userExisting.setEmail(userEntity.getEmail());
                    return userRepository.save(userExisting);
                }).orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }

    @Transactional
    public void deleteUser(String userID) {
        UserEntity userEntityDB = userRepository.findById(userID)
                .orElseThrow(() -> new UserException(UserError.USER_CANNOT_BE_DELETED));
        userRepository.delete(userEntityDB);
    }

    @Transactional
    public UserEntity patchUser(String userID, UserEntity userEntity) {
        return userRepository.findById(userID)
                .map(userExisting -> {
                    if (emailExistsExcludeId(userExisting.getEmail(), userID)) {
                        throw new UserException(UserError.EMAIL_ALREADY_EXISTS);
                    }
                    if (telephoneNumberExistsExcludeId(userExisting.getTelephoneNumber(), userID)) {
                        throw new UserException(UserError.TELEPHONE_ALREADY_EXISTS);
                    }
                    if (userEntity.getFirstName() != null) {
                        userExisting.setFirstName(userEntity.getFirstName());
                    }
                    if (userEntity.getLastName() != null) {
                        userExisting.setLastName(userEntity.getLastName());
                    }
                    if (userEntity.getTelephoneNumber() != null) {
                        userExisting.setTelephoneNumber(userEntity.getTelephoneNumber());
                    }
                    if (userEntity.getEmail() != null) {
                        userExisting.setEmail(userEntity.getEmail());
                    }
                    return userRepository.save(userExisting);
                }).orElseThrow(() -> new UserException(UserError.USER_NOT_FOUND));
    }

    public boolean telephoneNumberExistsExcludeId(String telephonNumber, String id) {
        return userRepository.findByTelephoneNumberAndUserIDNot(telephonNumber, id).isPresent();
    }

    public boolean emailExistsExcludeId(String email, String id) {
        return userRepository.findByEmailAndUserIDNot(email, id).isPresent();
    }

    public boolean telephoneNumberExists(String telephonNumber) {
        return userRepository.findByTelephoneNumber(telephonNumber).isPresent();
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean userIdExists(String userId) {
        return userRepository.findById(userId).isPresent();
    }

}

