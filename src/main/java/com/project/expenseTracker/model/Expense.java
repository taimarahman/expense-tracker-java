package com.project.expenseTracker.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Long expenseCategory;

    @Column(nullable = false)
    private Long userId;

    private String shop;
    private String location;
    private String description;

    public Expense(double amount, LocalDate date, Long expenseCategory, Long userId, String shop, String location, String description) {
        this.amount = amount;
        this.date = date;
        this.expenseCategory = expenseCategory;
        this.userId = userId;
        this.shop = shop;
        this.location = location;
        this.description = description;
    }
}
