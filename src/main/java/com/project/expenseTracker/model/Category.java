package com.project.expenseTracker.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(nullable = false)
    private String categoryName;

    @Column(nullable = false)
    private String description = "";

    private Long parentId;

    @Column(nullable = false)
    private Long createdBy;



    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }

    public Category(String categoryName, String description, Long createdBy) {
        this.categoryName = categoryName;
        this.description = description;
        this.createdBy = createdBy;
    }

    public Category(String categoryName, String description, Long parentId, Long createdBy) {
        this.categoryName = categoryName;
        this.description = description;
        this.parentId = parentId;
        this.createdBy = createdBy;
    }


}
