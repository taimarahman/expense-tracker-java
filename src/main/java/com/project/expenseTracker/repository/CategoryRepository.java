package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentId(Long categoryId);
    List<Category> findByParentIdAndCreatedByIn(Long parentId, Collection<Long> createdBy);

    @Query("SELECT c FROM Category c WHERE c.parentId IS NULL AND c.createdBy IN (:adminId,:currentUserId)")
    List<Category> findAllCategoryByUserId(Long currentUserId, Long adminId);
}
