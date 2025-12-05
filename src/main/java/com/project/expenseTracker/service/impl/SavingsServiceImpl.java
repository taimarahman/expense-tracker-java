package com.project.expenseTracker.service.impl;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.dto.request.SavingsReqData;
import com.project.expenseTracker.dto.response.ResponseBaseData;
import com.project.expenseTracker.dto.response.ResponseSuccessData;
import com.project.expenseTracker.dto.response.SavingsDetailsData;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.exception.ResourceNotFoundException;
import com.project.expenseTracker.model.Savings;
import com.project.expenseTracker.repository.SavingsRepository;
import com.project.expenseTracker.repository.UserRepository;
import com.project.expenseTracker.service.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SavingsServiceImpl implements SavingsService {

    @Autowired
    SavingsRepository savingsRepo;

    @Autowired
    UserRepository userRepo;

    @Override
    public ResponseBaseData saveUpdateSavings(SavingsReqData reqData, Long currentUserId) {

        if (!userRepo.existsById(currentUserId)) {
            throw new ResourceNotFoundException("User not found:");
        }

        if (reqData.getSavingsId() != null) {
            Savings savings = savingsRepo.findById(reqData.getSavingsId()).orElseThrow(
                    () -> new ResourceNotFoundException("Savings not found"));

            if (!savings.getMonth().equals(reqData.getMonth()) || !savings.getYear().equals(reqData.getYear())) {
                throw new ForbiddenException("Month or year cannot be changed.");
            }

            savings.setAmount(reqData.getAmount());
            savings.setTitle(reqData.getTitle());
            savingsRepo.save(savings);

            return new ResponseSuccessData("Savings updated successfully!", HttpStatus.OK);
        }

        Savings savings = new Savings().builder()
                .amount(reqData.getAmount())
                .title(reqData.getTitle())
                .month(reqData.getMonth())
                .year(reqData.getYear())
                .userId(currentUserId)
                .build();

        savingsRepo.save(savings);

        return new ResponseSuccessData("Savings saved successfully!", HttpStatus.CREATED);
    }

    @Override
    public ResponseBaseData getSavingsDetails(Long currentUserId, Long savingsId) {

        Savings savings = savingsRepo.findById(savingsId).orElseThrow(
                () -> new ResourceNotFoundException("Savings not found")
        );
        if(savings.getUserId().equals(currentUserId)){
            throw new ForbiddenException("You are not authorized to view this income");
        }

        return new ResponseSuccessData(savings.toSavingsDetailsData(), "Savings found successfully!", HttpStatus.FOUND);
    }

    @Override
    public ResponseBaseData getSavingsDetails(Long currentUserId, Integer month, Integer year) {

        List<Savings> savingsList = (month != null && year != null) ?
                savingsRepo.findByUserIdAndMonthAndYear(currentUserId, month, year) : new ArrayList<>();

        List<SavingsDetailsData> detailsData = new ArrayList<>();
        if(!savingsList.isEmpty()){
            detailsData = savingsList.stream().map(Savings::toSavingsDetailsData).toList();
        }

        return new ResponseSuccessData(detailsData, ResponseMessageConstants.DATA_FOUND, HttpStatus.FOUND);

    }

    @Override
    public ResponseBaseData deleteSavings(Long currentUserId, Long savingsId) {
        Savings savings = savingsRepo.findById(savingsId).orElseThrow(
                () -> new ResourceNotFoundException("Savings not found"));

        savingsRepo.delete(savings);

        return new ResponseSuccessData("Savings deleted successfully!", HttpStatus.OK);
    }
}
