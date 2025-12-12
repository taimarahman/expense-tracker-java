package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.IncomeReqData;
import com.project.expenseTracker.dto.response.*;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.service.IncomeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebAPIUrlConstants.INCOME_API)
public class IncomeController {

    private final IncomeService incomeService;

    private Long getCurrentUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("currentUserId");
        if (userId == null) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        return userId;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<ApiResponse> saveUpdateIncome(@Valid @RequestBody IncomeReqData reqData, HttpSession session) {

        return ResponseEntity.ok(incomeService.saveUpdateMonthlyIncome(reqData, session));
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<ApiResponse> getMonthlyDetails(@RequestParam(name = "month", required = false) Integer month,
                                                    @RequestParam(name = "year", required = false) Integer year,
                                                    HttpSession session) {

        return ResponseEntity.ok(incomeService.getMonthlyDetails(getCurrentUserId(session), month, year));

    }

    @GetMapping(value = WebAPIUrlConstants.INCOME_DETAILS_API, produces = "application/json")
    public ResponseEntity<ApiResponse> getIncomeDetails(@PathVariable Long incomeId, HttpSession session) {

        return ResponseEntity.ok(incomeService.getIncomeDetails(getCurrentUserId(session), incomeId));
    }

    @GetMapping(value = WebAPIUrlConstants.INCOME_YEARLY_DETAILS_API, produces = "application/json")
    public ResponseEntity<ApiResponse> getYearlyDetails(@PathVariable Integer year, HttpSession session) {

        return ResponseEntity.ok(SuccessResponse.of(
                incomeService.getYearlyIncomeDetails(getCurrentUserId(session), year),
                ResponseMessageConstants.DATA_FOUND
                ));
    }

    @DeleteMapping(value = WebAPIUrlConstants.INCOME_DETAILS_API, produces = "application/json")
    public ResponseEntity<ApiResponse> deleteIncome(@PathVariable Long incomeId, HttpSession session) {

        return ResponseEntity.ok(incomeService.deleteIncome(getCurrentUserId(session), incomeId));
    }
}
