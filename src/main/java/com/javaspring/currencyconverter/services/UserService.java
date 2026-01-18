package com.javaspring.currencyconverter.services;

import com.javaspring.currencyconverter.entities.User;
import com.javaspring.currencyconverter.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> findAll(int page, int size) {
        log.info("Fetching all users - page: {}, size: {}", page, size);
        try {
            Pageable pageable = PageRequest.of(page, size);
            return userRepository.findAll(pageable);
        } catch (Exception e) {
            log.error("Error fetching users: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch users");
        }
    }

    public User createUser(User user) {
        try {
            log.info("Creating new user: {}", user.getUsername());
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error while creating a new user: {}", e.getMessage());
            throw new RuntimeException("Failed to create user");
        }
    }

    public User getById(Long id) {
        try {
            log.info("Getting user by ID: {}", id);
            return userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        } catch (Exception e) {
            log.error("Error while getting user by ID: {}", e.getMessage());
            throw new RuntimeException("Failed to get user");
        }
    }

    public void deleteById(Long id) {  // FIX: Changed return type from User to void
        try {
            log.info("Deleting user with ID: {}", id);
            // FIX: deleteById returns void, not User
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error while deleting the user: {}", e.getMessage());
            throw new RuntimeException("Failed to delete user");
        }
    }

    public User update(Long id, User updatedUser) {
        try {
            log.info("Updating user with ID: {}", id);
            Optional<User> existingUser = userRepository.findById(id);

            if (existingUser.isPresent()) {
                User userToUpdate = existingUser.get();

                if (updatedUser.getUsername() != null) {
                    userToUpdate.setUsername(updatedUser.getUsername());
                }
                if (updatedUser.getAddress() != null) {
                    userToUpdate.setAddress(updatedUser.getAddress());
                }
                if (updatedUser.getEmail() != null) {
                    userToUpdate.setEmail(updatedUser.getEmail());
                }

                return userRepository.save(userToUpdate);
            } else {
                log.info("User with ID {} not found", id);
                throw new RuntimeException("User not found with ID: " + id);
            }
        } catch (Exception e) {
            log.error("Error occurred while updating the user: {}", e.getMessage());
            throw new RuntimeException("Failed to update user");
        }
    }
}