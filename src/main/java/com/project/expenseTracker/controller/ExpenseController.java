package com.project.expenseTracker.controller;

import com.project.expenseTracker.constants.ResponseMessageConstants;
import com.project.expenseTracker.constants.WebAPIUrlConstants;
import com.project.expenseTracker.dto.request.ExpenseInfoReqData;
import com.project.expenseTracker.dto.response.ResponseHandler;
import com.project.expenseTracker.model.Expense;
import com.project.expenseTracker.service.ExpenseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(WebAPIUrlConstants.EXPENSE_API)
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping(value = WebAPIUrlConstants.EXPENSE_CREATE_API, produces = "application/json")
    public ResponseEntity<Object> addExpense(@RequestBody ExpenseInfoReqData reqData, HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");

            if(Objects.nonNull(currentUserId)){
                String successMsg = expenseService.addExpense(reqData, currentUserId);

                if(Objects.nonNull(successMsg) && !successMsg.isEmpty()){
                    return ResponseHandler.generateResponse(successMsg, HttpStatus.OK);
                } else
                    return ResponseHandler.generateResponse(ResponseMessageConstants.ERROR, HttpStatus.OK);
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

            if(Objects.nonNull(currentUserId)){
                expenseService.deleteExpense(id, currentUserId);
                return ResponseHandler.generateResponse(ResponseMessageConstants.DELETE_SUCCESS, HttpStatus.OK);
            } else
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping(value = WebAPIUrlConstants.EXPENSE_UPDATE_API, produces = "application/json")
    public ResponseEntity<Object> updateExpense(@RequestBody Expense reqData, HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");

            if(Objects.nonNull(currentUserId)){
                String successMsg = expenseService.updateExpense(reqData, currentUserId);

                if(Objects.nonNull(successMsg) && !successMsg.isEmpty()){
                    return ResponseHandler.generateResponse(successMsg, HttpStatus.OK);
                } else
                    return ResponseHandler.generateResponse(ResponseMessageConstants.ERROR, HttpStatus.OK);

            } else
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = WebAPIUrlConstants.EXPENSE_LIST_API, produces = "application/json")
    public ResponseEntity<Object> getExpenseList(HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");

            if(Objects.nonNull(currentUserId)){
                List<Expense> list = expenseService.getExpenseList(currentUserId);

                if(Objects.nonNull(list) && list.size() > 0){
                    return ResponseHandler.generateResponse(list, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
                } else
                    return ResponseHandler.generateResponse(ResponseMessageConstants.ERROR, HttpStatus.OK);
            } else
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = WebAPIUrlConstants.EXPENSE_MONTHLY_LIST_API, produces = "application/json")
    public ResponseEntity<Object> getMonthlyExpenseList(@RequestParam(name="month", required = false, defaultValue = "-1") String month, @RequestParam(name="year", required = false) String year, HttpSession session) {
        try {
            Long currentUserId = (Long) session.getAttribute("currentUserId");

            if(Objects.nonNull(currentUserId)){

                List<Expense> list = expenseService.getMonthlyExpenseList(currentUserId, month, year);

                if(Objects.nonNull(list) && list.size() > 0){
                    return ResponseHandler.generateResponse(list, ResponseMessageConstants.DATA_FOUND, HttpStatus.OK);
                } else
                    return ResponseHandler.generateResponse(ResponseMessageConstants.ERROR, HttpStatus.OK);
            } else
                return ResponseHandler.generateResponse(ResponseMessageConstants.UNAUTHORIZED_USER, HttpStatus.UNAUTHORIZED);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseHandler.generateResponse(ResponseMessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);

        }
    }


}
