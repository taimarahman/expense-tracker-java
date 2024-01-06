package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    public Users findUsersByUsername(String username);
    public Boolean existsByEmail(String email);
    public Boolean existsByUsername(String username);

    @Query("SELECT U.userId FROM Users U WHERE U.username = :username")
    public Long findIdByUsername(String username);


}
