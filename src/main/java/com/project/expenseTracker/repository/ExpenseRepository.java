package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserId(Long currentUserId);

    @Query(value = "SELECT e FROM Expense e WHERE e.userId = :currentUserId\n" +
            "AND EXTRACT(MONTH FROM e.date) = :month\n" +
            "AND EXTRACT(YEAR FROM e.date) = :year")
    List<Expense> findAllByUserIdAndMonth(Long currentUserId, Integer month, Integer year);
}
