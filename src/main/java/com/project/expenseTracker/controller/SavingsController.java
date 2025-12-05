package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.SavingsReqData;
import com.project.expenseTracker.exception.ForbiddenException;
import com.project.expenseTracker.service.SavingsService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(WebAPIUrlConstants.SAVINGS_API)
public class SavingsController {

    @Autowired
    SavingsService savingsService;

    private Long getCurrentUserId(HttpSession session) {
        Long userId = (Long) session.getAttribute("currentUserId");
        if (userId == null) {
            throw new ForbiddenException(ResponseMessageConstants.UNAUTHORIZED_USER);
        }
        return userId;
    }

    @PostMapping
    public ResponseEntity<Object> saveUpdateSavings(@Valid @RequestBody SavingsReqData reqData, HttpSession session) {
        return ResponseEntity.ok(savingsService.saveUpdateSavings(reqData, getCurrentUserId(session)));
    }


    @GetMapping(value = WebAPIUrlConstants.SAVINGS_BY_ID_API)
    public ResponseEntity<Object> getSavingsDetails(@PathVariable Long savingsId, HttpSession session) {
        return ResponseEntity.ok(savingsService.getSavingsDetails(getCurrentUserId(session), savingsId));
    }

    @GetMapping
    public ResponseEntity<Object> getMonthlySavings(
            @RequestParam(name = "month", required = false, defaultValue = "-1") Integer month,
            @RequestParam(name = "year", required = false) Integer year,
            HttpSession session) {
        return ResponseEntity.ok(savingsService.getSavingsDetails(getCurrentUserId(session), month, year));
    }

    @DeleteMapping(value = WebAPIUrlConstants.SAVINGS_BY_ID_API)
    public ResponseEntity<Object> deleteSavings(@PathVariable Long savingsId, HttpSession session) {
        return ResponseEntity.ok(savingsService.deleteSavings(getCurrentUserId(session), savingsId));
    }

}
