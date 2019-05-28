package com.tallrye.wlearn.exception;

import com.tallrye.wlearn.dto.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ApiResponseDto> handleResourceNotFound(ResourceNotFoundException ex) {
        final ApiResponseDto response = new ApiResponseDto(false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CreatedByException.class)
    protected ResponseEntity<ApiResponseDto> handleCreatedBy(CreatedByException ex) {
        final ApiResponseDto response = new ApiResponseDto(false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ApiResponseDto> handleUsernameNotFound(UsernameNotFoundException ex) {
        final ApiResponseDto response = new ApiResponseDto(false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotValidTopicException.class)
    protected ResponseEntity<ApiResponseDto> handleNotValidTopicException(NotValidTopicException ex) {
        final ApiResponseDto response = new ApiResponseDto(false, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
