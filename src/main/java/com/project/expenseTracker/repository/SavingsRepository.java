package com.project.expenseTracker.repository;

import com.project.expenseTracker.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {

    List<Savings> findByUserIdAndMonthAndYear(Long currentUserId, Integer month, Integer year);

    @Query("SELECT SUM(s.amount) as totalSavings, s.month as month, s.year as year " +
            "FROM Savings s " +
            "WHERE s.userId = :userId " +
            "AND s.year = :year " +
            "GROUP BY s.month, s.year")
    List<Map<String, Object>> getYearlySavings(Long userId, Integer year);


}
