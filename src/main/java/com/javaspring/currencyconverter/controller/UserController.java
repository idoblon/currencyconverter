package com.javaspring.currencyconverter.controller;

import com.javaspring.currencyconverter.entities.User;
import com.javaspring.currencyconverter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Page<User>> getAllUser(@RequestParam(defaultValue = "0")int page,
                                                 @RequestParam(defaultValue = "9")int size){
        log.info("Fetching all users - page: {}, size: {}", page, size);
        Page<User> users = userService.findAll(page, size);
        return ResponseEntity.ok(users);
    }
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody User user){
        log.info("Creating new user: {}", user.getUsername());
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        log.info("Fetching user by ID: {}", id);
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        log.info("User Deleted : {}", id);
        try{
            userService.deleteById(id);
            return ResponseEntity.ok("The user is deleted." + id);
        }catch (Exception e){
            log.error("Error deleting the user: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found" + id);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        log.info("Updating User by ID: {}", id);
        try{
            User updated = userService.update(id, user);
            return ResponseEntity.ok(updated);

        }catch (Exception e){
            log.error("Error updating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
