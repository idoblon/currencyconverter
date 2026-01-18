package com.javaspring.currencyconverter.repository;

import com.javaspring.currencyconverter.entities.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {
}
