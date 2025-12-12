package com.project.expenseTracker.repository;

import com.project.expenseTracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentId(Long categoryId);

    List<Category> findAllByUser_UserIdAndParentIdIsNull(Long userId);

    @Query("SELECT c FROM Category c WHERE " +
            "c.parentId IS NULL AND " +
            "c.user.userId = :userId")
    List<Category> findAllCategoryByUserId(Long userId);

    Optional<Category> findByCategoryIdAndUser_UserId(Long categoryId, Long userId);
}
