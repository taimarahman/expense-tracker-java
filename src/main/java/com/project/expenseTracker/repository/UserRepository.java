package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    public Boolean existsByEmail(String email);

    public Boolean existsByUsername(String username);

    @Query("SELECT U.userId FROM User U WHERE U.username = :username")
    public Long findIdByUsername(String username);


}
