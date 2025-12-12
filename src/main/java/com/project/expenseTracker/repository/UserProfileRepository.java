package com.project.expenseTracker.repository;

import com.project.expenseTracker.entity.UserProfileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileInfo, Long> {
}
