package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByUserIdAndMonthAndYear(Long currentUserId, Integer reqMonth, Integer reqYear);

    @Query("SELECT SUM(i.amount) as totalIncome, i.month as month, i.year as year " +
            "FROM Income i " +
            "WHERE i.userId = :userId " +
            "GROUP BY i.month, i.year")
    List<Map<String, Object>> getAllMonthlyIncomeSummaries(Long userId);
}
