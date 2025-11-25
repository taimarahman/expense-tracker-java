package com.project.expenseTracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsId;

    @Column(nullable = false)
    private BigDecimal savingsAmount;

    @Column(nullable = false)
    private Integer month;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Long userId;

    public Savings(BigDecimal savingsAmount, Integer month, Integer year, Long userId) {
        this.savingsAmount = savingsAmount;
        this.month = month;
        this.year = year;
        this.userId = userId;
    }
}
