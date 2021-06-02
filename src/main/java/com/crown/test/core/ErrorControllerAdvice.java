package com.crown.test.core;

import com.crown.test.core.data.Response;
import com.crown.test.core.exceptions.CustomerAlreadyExistException;
import com.crown.test.core.exceptions.CustomerNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

    @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<?> handleValidationException(ConstraintViolationException ex) {
        Response response = Response.of(ex.getMessage(), HttpStatus.BAD_REQUEST);
        ex.getConstraintViolations().forEach(e -> {
            response.error(e.getMessage());
            log.error("Invalid input - " + ex.getMessage() + " : " + e);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.of(ex.getMessage(), HttpStatus.NOT_FOUND).error(ex.getMessage()));
    }

    @ExceptionHandler(CustomerAlreadyExistException.class)
    ResponseEntity<?> handleCustomerAlreadyExistException(CustomerAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Response.of(ex.getMessage(), HttpStatus.CONFLICT).error(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.of(ex.getMessage(), HttpStatus.BAD_REQUEST).error(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.of(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).error(ex.getMessage()));
    }
}
