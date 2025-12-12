package com.project.expenseTracker.repository;

import com.project.expenseTracker.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByUser_UserIdAndMonthAndYear(Long currentUserId, Integer reqMonth, Integer reqYear);

    @Query("SELECT SUM(i.amount) as totalIncome, i.month as month, i.year as year " +
            "FROM Income i " +
            "WHERE i.user.userId = :userId " +
            "AND i.year = :year " +
            "GROUP BY i.month, i.year")
    List<Map<String, Object>> getYearlyIncome(Long userId, Integer year);

}
