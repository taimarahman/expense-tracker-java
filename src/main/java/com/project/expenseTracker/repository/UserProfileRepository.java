package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.UserProfileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileInfo, Integer> {
}
