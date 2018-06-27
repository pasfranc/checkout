package com.r3pi.assessment.checkout.controller.advice;

import static java.util.Objects.nonNull;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.r3pi.assessment.checkout.model.ErrorResponse;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
  
  @Autowired
  private MessageSource messageSource;
  
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleSpringValidation(MethodArgumentNotValidException me) {
    
    logger.error("Error: {}", me.getMessage());

    ErrorResponse errorResponse = new ErrorResponse(interpolateErrorMessage(me));
    
    logger.error("ErrorResponse with code[{}]: {}",HttpStatus.BAD_REQUEST, errorResponse);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
   
  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorResponse> handleFailure(Exception e) {
    
    logger.error("Generic Exception caught: {}", e.getCause());
    StringWriter stack = new StringWriter();
    e.printStackTrace(new PrintWriter(stack));
    logger.error("Stacktrace: {}", stack);
    logger.error("Error: {}", e.getMessage());
    ErrorResponse errorResponse = new ErrorResponse("Technical Error:" + e.getMessage());
    
    logger.error("ErrorResponse with code[{}]: {}",HttpStatus.INTERNAL_SERVER_ERROR, errorResponse);
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  private String interpolateErrorMessage(MethodArgumentNotValidException me) {
    String errorDescription = me.getMessage();
    for(FieldError fieldError : me.getBindingResult().getFieldErrors()){      
      String resolvedError = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
      if(nonNull(resolvedError)){
        errorDescription = resolvedError;
      }
    }
    return errorDescription;
  }
  
}


