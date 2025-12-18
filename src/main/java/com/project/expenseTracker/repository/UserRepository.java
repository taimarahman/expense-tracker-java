package com.project.expenseTracker.repository;

import com.project.expenseTracker.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

}
