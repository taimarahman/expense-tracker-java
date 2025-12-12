package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.SavingsDto;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.dto.response.SavingsResData;
import com.project.expenseTracker.dto.response.SuccessResponse;
import com.project.expenseTracker.entity.Savings;
import com.project.expenseTracker.entity.User;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.exception.ResourceNotFoundException;
import com.project.expenseTracker.repository.SavingsRepository;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.SavingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SavingsServiceImpl implements SavingsService {

    private final SavingsRepository savingsRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse saveUpdateSavings(SavingsDto reqData, Long currentUserId) {

        if (reqData.getSavingsId() != null) {
            Savings savings = savingsRepository.findById(reqData.getSavingsId()).orElseThrow(
                    () -> new ResourceNotFoundException("Savings not found"));

            if (!savings.getUser().getUserId().equals(currentUserId)) {
                throw new ForbiddenException("You are not authorized to update this savings");
            }

            if (!savings.getMonth().equals(reqData.getMonth()) || !savings.getYear().equals(reqData.getYear())) {
                throw new ForbiddenException("Month or year cannot be changed.");
            }

            savings.setAmount(reqData.getAmount());
            savings.setTitle(reqData.getTitle());
            savingsRepository.save(savings);

            return SuccessResponse.of("Savings updated successfully!");
        }
        // create
        User currentUser = userRepository.findById(currentUserId).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));

        Savings savings = Savings.builder()
                .amount(reqData.getAmount())
                .title(reqData.getTitle())
                .month(reqData.getMonth())
                .year(reqData.getYear())
                .user(currentUser)
                .build();

        savingsRepository.save(savings);

        return SuccessResponse.of("Savings saved successfully!", HttpStatus.CREATED);
    }

    @Override
    public ApiResponse getSavingsDetails(Long currentUserId, Long savingsId) {

        Savings savings = savingsRepository.findById(savingsId).orElseThrow(
                () -> new ResourceNotFoundException("Savings not found")
        );
        if (!savings.getUser().getUserId().equals(currentUserId)) {
            throw new ForbiddenException("You are not authorized to view this income");
        }

        return SuccessResponse.of(savings.toSavingsDto(), "Savings found successfully!");
    }

    @Override
    public ApiResponse getSavingsDetails(Long currentUserId, Integer month, Integer year) {

        List<Savings> savingsList = (month != null && year != null) ?
                savingsRepository.findByUser_UserIdAndMonthAndYear(currentUserId, month, year) : new ArrayList<>();

        List<SavingsDto> detailsData = new ArrayList<>();
        if (!savingsList.isEmpty()) {
            detailsData = savingsList.stream().map(Savings::toSavingsDto).toList();
        }

        return SuccessResponse.of(detailsData, ResponseMessageConstants.DATA_FOUND);

    }

    @Override
    public ApiResponse deleteSavings(Long currentUserId, Long savingsId) {
        Savings savings = savingsRepository.findById(savingsId).orElseThrow(
                () -> new ResourceNotFoundException("Savings not found"));

        savingsRepository.delete(savings);

        return SuccessResponse.of("Savings deleted successfully!");
    }

    @Override
    public ApiResponse getYearlyDetails(Long currentUserId, Integer year) {
        List<Map<String, Object>> rows = (year != null)
                ? savingsRepository.getYearlySavings(currentUserId, year)
                : new ArrayList<>();
        List<SavingsResData> savingsList = new ArrayList<>();
        if (!rows.isEmpty()) {
            savingsList = rows.stream()
                    .map(row -> {
                        SavingsResData savings = SavingsResData.builder()
                                .month((Integer) row.get("month"))
                                .year((Integer) row.get("year"))
                                .totalAmount((BigDecimal) row.get("totalSavings"))
                                .build();

                        List<Savings> monthlyList = savingsRepository.findByUser_UserIdAndMonthAndYear(
                                currentUserId, savings.getMonth(), savings.getYear()
                        );
                        if (!monthlyList.isEmpty()) {
                            savings.setDetails(monthlyList.stream()
                                    .map(Savings::toSavingsDto)
                                    .toList());
                        }

                        return savings;
                    }).toList();

        }

        return SuccessResponse.of(savingsList, ResponseMessageConstants.DATA_FOUND);
    }
}
