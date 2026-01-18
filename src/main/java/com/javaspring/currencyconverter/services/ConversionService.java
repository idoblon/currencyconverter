package com.javaspring.currencyconverter.services;

import com.javaspring.currencyconverter.entities.Conversion;
import com.javaspring.currencyconverter.entities.User;
import com.javaspring.currencyconverter.repository.ConversionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class ConversionService {

    @Autowired
    private ConversionRepository conversionRepository;

    public Page<Conversion> findAll(int page, int size){
        log.info("Fetching all users - page: {}, size: {}", page, size );
        try{
            Pageable pageable = PageRequest.of(page, size);
            return conversionRepository.findAll(pageable);
        }catch (Exception e){
            log.error("Error while fetching ID : {}", e.getMessage());
            throw new RuntimeException("Failed to fetch the conversion history.");
        }
    }
    public Conversion createConversion(Conversion conversion){
        try{
            log.info("Creating a new Conversion: {}", conversion.getAmount());
            return conversionRepository.save(conversion);
        }catch (Exception e){
            log.error("Error while creating a new Conversion: {}", e.getMessage());
            throw new RuntimeException("Failed to create Conversion");
        }
    }

    public Conversion getById(Long id){
        try{
            log.info("Getting Conversion by ID: {}", id);
            return conversionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        }catch (Exception e){
            log.error("Error while getting conversion by ID: {}", e.getMessage());
            throw new RuntimeException("Failed to get conversion");
        }
    }

    public Conversion deleteById(Long id) {
        try {
            log.info("Deleting conversion with ID: {}", id);

            Conversion conversion = conversionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Conversion not found"));

            conversionRepository.deleteById(id);

            return conversion;
        } catch (Exception e) {
            log.error("Error while deleting the conversion: {}", e.getMessage());
            throw new RuntimeException("Failed to delete conversion");
        }
    }
    public Conversion update(Long id, Conversion updatedConversion) {
        try {
            System.out.println("Updating conversion with ID: " + id);


            Conversion existingConversion = conversionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Conversion not found with ID: " + id));


            if (updatedConversion.getAmount() != 0) {
                existingConversion.setAmount(updatedConversion.getAmount());
            }
            if (updatedConversion.getExchangeRate() != 0) {
                existingConversion.setExchangeRate(updatedConversion.getExchangeRate());
            }
            if (updatedConversion.getConvertedAmount() != 0) {
                existingConversion.setConvertedAmount(updatedConversion.getConvertedAmount());
            }

            return conversionRepository.save(existingConversion);
        } catch (Exception e) {
            System.out.println("Error occurred while updating the conversion: " + e.getMessage());
            throw new RuntimeException("Failed to update conversion");
        }
    }
}
