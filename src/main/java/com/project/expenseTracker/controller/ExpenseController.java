package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.ExpenseInfoRequest;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.service.ExpenseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(WebAPIUrlConstants.EXPENSE_API)
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping(value = WebAPIUrlConstants.EXPENSE_CREATE_API, produces = "application/json")
    public ResponseEntity<Object> addExpense(@RequestBody ExpenseInfoRequest reqData, HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");

            if(currentUserId != null){
                String successMsg = expenseService.addExpense(reqData, currentUserId);
                return ResponseHandler.generateResponse(successMsg, HttpStatus.OK);
            } else {
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping(value = WebAPIUrlConstants.EXPENSE_DELETE_API, produces = "application/json")
    public ResponseEntity<Object> deleteExpense(@PathVariable Long id, HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");

            if(currentUserId != null){
                expenseService.deleteExpense(id, currentUserId);
                return ResponseHandler.generateResponse(ResponseMessageConstants.DELETE_SUCCESS, HttpStatus.OK);
            } else
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

        }
    }
}
