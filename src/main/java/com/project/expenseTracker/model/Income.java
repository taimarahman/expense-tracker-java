package com.project.expenseTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeId;

    @Column(nullable = false)
    private BigDecimal incomeAmount;

    @Column(nullable = false)
    private String incomeSource;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Long userId;

    public Income(BigDecimal incomeAmount, String incomeSource, Integer month, Integer year, Long userId) {
        this.incomeAmount = incomeAmount;
        this.incomeSource = incomeSource;
        this.month = month;
        this.year = year;
        this.userId = userId;
    }
}
