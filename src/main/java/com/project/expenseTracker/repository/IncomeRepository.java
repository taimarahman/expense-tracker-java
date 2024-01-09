package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByUserIdAndMonthAndYear(Long currentUserId, Integer reqMonth, Integer reqYear);
}
