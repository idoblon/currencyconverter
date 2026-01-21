package com.javaspring.currencyconverter.controller;

import com.javaspring.currencyconverter.entities.Conversion;
import com.javaspring.currencyconverter.pojo.response.CurrencyRateResponsePojo;
import com.javaspring.currencyconverter.services.ConversionService;
import com.javaspring.currencyconverter.services.CurrencyConversionService;
import com.javaspring.currencyconverter.util.CurrencyConversionApiCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ConversionController {

    private final ConversionService conversionService;
    private final CurrencyConversionService currencyConversionService;

    @GetMapping("/conversionhistory")
    public ResponseEntity<Page<Conversion>> getAllConversionHistory(@RequestParam(defaultValue = "0")int page,
                                                                    @RequestParam(defaultValue = "10")int size){
        log.info("An error occurred while fetching conversionhistory - page: {}, size: {}", page, size );
        Page<Conversion> conversionHistories = conversionService.findAll(page, size);
        return ResponseEntity.ok(conversionHistories);
    }

    @PostMapping("/conversion/add")
    public ResponseEntity<Conversion> createConversion(@RequestBody Conversion conversionHistory){
        log.info("Creating a new ConversionHistory: {}" , conversionHistory.getAmount());
        Conversion savedConversion = conversionService.createConversion(conversionHistory);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedConversion);
    }

    @GetMapping("/conversion/{id}")
    public ResponseEntity<Conversion> getById(@PathVariable Long id){
        log.info("Fetching the conversionhistory by ID: {}", id);
        Conversion conversionHistory = conversionService.getById(id);
        return ResponseEntity.ok(conversionHistory);
    }

    @DeleteMapping("/conversion/delete/{id}")
    public ResponseEntity<String> deleteConversionHistory(@PathVariable Long id){
        log.info("Deleting the conversionhistory by ID: {}", id);
        try{
            conversionService.deleteById(id);
            return ResponseEntity.ok("The conversion history is deleted." + id);
        }catch (Exception e){
            log.error("Error deleting the history: {}" ,e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found"+ id);
        }
    }

    @PutMapping("/cpnversion/update/{id}")
    public ResponseEntity<Conversion> updateConversion(@PathVariable Long id, @RequestBody Conversion conversionHistory){
        log.info("Error occurred while updating ID: {}", id);
        try{
            Conversion updated = conversionService.update(id, conversionHistory);
            return ResponseEntity.ok(updated);
        }catch (Exception e){
            log.error("Error updating ID: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("currencyRates")
    public ResponseEntity<CurrencyRateResponsePojo> getCurrencyRates(){
        return ResponseEntity.ok(currencyConversionService.getCurrencyConversionRate());
    }
}
