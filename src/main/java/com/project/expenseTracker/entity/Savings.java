package com.project.expenseTracker.entity;

import com.project.expenseTracker.dto.response.SavingsDetailsData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, precision = 10, scale = 2)
    private Integer month;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public SavingsDetailsData toSavingsDetailsData() {
        return SavingsDetailsData.builder()
                .savingsId(this.getSavingsId())
                .amount(this.getAmount())
                .title(this.getTitle())
                .month(this.getMonth())
                .year(this.getYear())
                .build();
    }
}
