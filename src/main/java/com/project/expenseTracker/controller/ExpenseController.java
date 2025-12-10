package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.ExpenseDto;
import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.dto.response.SuccessResponse;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.model.Expense;
import com.project.expenseTracker.service.ExpenseService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping(WebAPIUrlConstants.EXPENSE_API)
public class ExpenseController {

    private final ExpenseService expenseService;

    private Long getCurrentUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("currentUserId");
        if (userId == null) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        return userId;
    }

    @PostMapping
    public ResponseEntity<Object> saveUpdateExpense(@RequestBody ExpenseDto reqData, HttpSession session) {
        return ResponseEntity.ok(expenseService.saveUpdateExpense(reqData, getCurrentUserId(session)));
    }

    @GetMapping(value = WebAPIUrlConstants.EXPENSE_BY_ID_API, produces = "application/json")
    public ResponseEntity<Object> getExpenseById(@PathVariable Long expenseId, HttpSession session) {
        return ResponseEntity.ok(expenseService.getExpenseById(expenseId, getCurrentUserId(session)));
    }

    @DeleteMapping(value = WebAPIUrlConstants.EXPENSE_BY_ID_API, produces = "application/json")
    public ResponseEntity<Object> deleteExpense(@PathVariable Long expenseId, HttpSession session) {
        return ResponseEntity.ok(expenseService.deleteExpense(expenseId, getCurrentUserId(session)));
    }

    @GetMapping(value = WebAPIUrlConstants.EXPENSE_LIST_API, produces = "application/json")
    public ResponseEntity<ApiResponse> getExpenseList(HttpSession session) {
        List<Expense> list = expenseService.getExpenseList(getCurrentUserId(session));
        return ResponseEntity.ok(SuccessResponse.of(list, ResponseMessageConstants.DATA_FOUND));
    }

    @GetMapping
    public ResponseEntity<Object> getMonthlyExpenseList(@RequestParam(name="month") Integer month,
                                                        @RequestParam(name="year") Integer year, HttpSession session) {
        return ResponseEntity.ok(SuccessResponse.of(
                expenseService.getMonthlyExpenseList(getCurrentUserId(session), month, year),
                ResponseMessageConstants.DATA_FOUND));
    }

}
