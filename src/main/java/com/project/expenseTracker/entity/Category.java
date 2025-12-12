package com.project.expenseTracker.entity;


import com.project.expenseTracker.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "categories",
    indexes = {
        @Index(name="idx_category_key", columnList = "category_key", unique = true),
        @Index(name="idx_category_parent_id", columnList = "parent_id"),
        @Index(name="idx_category_active", columnList = "is_active")})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "category_key", unique = true, nullable = false, updatable = false)
    private String key;

    @Column(nullable = false)
    private String name;

    private String description = "";

    private String icon;

    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long createdBy;

    private Long updatedBy;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean isActive;


    public CategoryDto toCategoryDto(){
        return CategoryDto.builder()
                .categoryId(this.getCategoryId())
                .key(this.getKey())
                .name(this.getName())
                .description(this.getDescription())
                .icon(this.getIcon())
                .parentId(this.getParentId())
                .isActive(this.getIsActive())
                .build();
    }

}
