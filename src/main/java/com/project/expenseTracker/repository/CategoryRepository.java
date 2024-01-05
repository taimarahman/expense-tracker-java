package com.project.expenseTracker.repository;

import com.project.expenseTracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByParentId(Integer categoryId);

    @Query("SELECT c FROM Category c WHERE c.parentId IS NULL")
    List<Category> findAllWhereParentIdIsNull();
}
