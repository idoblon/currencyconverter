package com.javaspring.currencyconverter.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "conversionhistory")
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @ManyToOne
    private User user;
    private String fromCurrency;
    private String toCurrency;
    private float amount;
    private float convertedAmount;
    private float exchangeRate;
}
