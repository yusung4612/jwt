package com.example.temipj.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionHandler {

    // 바인드 에러
    @ExceptionHandler({BindException.class})
    protected ResponseEntity<Object> handleServerException(BindException ex) {
        RestApiException error = new RestApiException(ErrorCode.BIND_FAILS.name(), ErrorCode.BIND_FAILS.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    // 회원가입 정보 확인
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleApiRequestException(MethodArgumentNotValidException ex) {
        List<RestApiException> errors = new ArrayList<>();

        for (FieldError field : ex.getBindingResult().getFieldErrors()) {
            errors.add(new RestApiException(field.getField(), field.getDefaultMessage()));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    // Null일 때
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointException(NullPointerException ex){
        RestApiException error = new RestApiException(ErrorCode.NOT_VALUE_AT.name(),ErrorCode.NOT_VALUE_AT.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    // Element 없을 때
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex){
        RestApiException error = new RestApiException(ErrorCode.NOT_VALUE_AT.name(),ErrorCode.NOT_VALUE_AT.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}