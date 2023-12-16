package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    public Users findUsersByUsernameAndPassword(String username, String password);
    public Boolean existsByEmail(String email);
    public Boolean existsByUsername(String username);
}
