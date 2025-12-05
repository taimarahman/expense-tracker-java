package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {

    List<Savings> findByUserIdAndMonthAndYear(Long currentUserId, Integer month, Integer year);
}
