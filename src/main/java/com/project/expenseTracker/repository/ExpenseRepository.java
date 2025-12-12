package com.project.expenseTracker.repository;

import com.project.expenseTracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUser_UserId(Long currentUserId);

    @Query(value = "SELECT e FROM Expense e WHERE e.user.userId = :currentUserId\n" +
            "AND EXTRACT(MONTH FROM e.date) = :month\n" +
            "AND EXTRACT(YEAR FROM e.date) = :year")
    List<Expense> findAllByUser_UserIdAndMonth(Long currentUserId, Integer month, Integer year);
}
