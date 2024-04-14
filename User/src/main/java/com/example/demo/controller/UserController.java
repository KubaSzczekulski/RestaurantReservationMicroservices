package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserEntity createUser(@RequestBody UserEntity userEntity, @RequestHeader String userId) {
        return userService.addUser(userEntity, userId);
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> userEntities = userService.getAllUsers();
        return ResponseEntity.ok(userEntities);
    }

    @GetMapping
    public ResponseEntity<UserEntity> getUserById(@RequestHeader String userID) {
        UserEntity userEntity = userService.getUserById(userID);
        return ResponseEntity.ok(userEntity);
    }

    @PutMapping
    public ResponseEntity<UserEntity> updateUser(@RequestHeader String userID, @RequestBody UserEntity userEntity) {
        UserEntity updatedUserEntity = userService.updateUser(userID, userEntity);
        return ResponseEntity.ok(updatedUserEntity);
    }

    @PatchMapping
    public ResponseEntity<UserEntity> patchUser(@RequestHeader String userID, @RequestBody UserEntity userEntity) {
        UserEntity patchedUserEntity = userService.patchUser(userID, userEntity);
        return ResponseEntity.ok(patchedUserEntity);
    }

    @DeleteMapping
    public ResponseEntity<UserEntity> deleteUser(@RequestHeader String userID) {

        userService.deleteUser(userID);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/checkFields")
    public ResponseEntity<Boolean> checkUserFields(@RequestHeader String userId) {
        boolean areAllFieldsSet = userService.areAllFieldsSet(userId);
        return ResponseEntity.ok(areAllFieldsSet);
    }
}
