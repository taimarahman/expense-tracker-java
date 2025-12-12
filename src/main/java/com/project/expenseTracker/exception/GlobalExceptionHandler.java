package com.project.expenseTracker.exception;

import com.project.expenseTracker.dto.response.ApiResponse;
import com.project.expenseTracker.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Conflict (duplicate username or email)
    @ExceptionHandler({
            UsernameAlreadyExistException.class,
            EmailAlreadyExistsException.class
    })
    public ResponseEntity<ApiResponse> handleConflict(RuntimeException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse<>(ex.getMessage(), HttpStatus.CONFLICT));
    }
//    @ExceptionHandler(UsernameAlreadyExistException.class)
//    public ResponseEntity<Object> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex) {
//        return ResponseHandler.generateErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(EmailAlreadyExistsException.class)
//    public ResponseEntity<Object> handleEmailAlreadyExistException(EmailAlreadyExistsException ex) {
//        return ResponseHandler.generateErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
//    }
    // Not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundExceptions(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse<>(ex.getMessage(), HttpStatus.NOT_FOUND));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.internalServerError().body(
                new ErrorResponse<>("Something went wrong. Please try again later.",
                        HttpStatus.INTERNAL_SERVER_ERROR, ex.toString()));
    }

    // @Valid failures
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String firstErrorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        // for showing all validation errors
//        String message = ex.getBindingResult().getFieldErrors().stream()
//                .map(error -> error.getField() + ": " + error.getDefaultMessage())
//                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(new ErrorResponse<>("Validation failed: " + firstErrorMessage, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse> handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse<>(ex.getMessage(), HttpStatus.FORBIDDEN));
    }
}
