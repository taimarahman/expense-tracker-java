package com.project.expenseTracker.exception;

import com.project.expenseTracker.dto.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<Object> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex) {
        return ResponseHandler.generateErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAlreadyExistException(EmailAlreadyExistsException ex) {
        return ResponseHandler.generateErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundExceptions(ResourceNotFoundException ex) {
        return ResponseHandler.generateErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        return ResponseHandler.generateErrorResponse("Something went wrong. Please try again later.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String firstErrorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseHandler.generateErrorResponse(firstErrorMessage, HttpStatus.BAD_REQUEST);
    }
}
