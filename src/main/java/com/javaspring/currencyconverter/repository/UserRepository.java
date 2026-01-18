package com.javaspring.currencyconverter.repository;

import com.javaspring.currencyconverter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
