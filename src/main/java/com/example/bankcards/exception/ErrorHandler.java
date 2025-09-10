package com.example.bankcards.exception;

import com.example.bankcards.dto.ApiErrorDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorDto handleNotFoundException(final NotFoundException e) {
        return ApiErrorDto.builder()
                .message(e.getMessage())
                .reason("Not Found")
                .status(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto handleIllegalArgumentException(final IllegalArgumentException e) {
        return ApiErrorDto.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Bad Request")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorDto handleConflictException(final ConflictException e) {
        return ApiErrorDto.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Conflict situation with input parameter")
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorDto handleIllegalStateException(final IllegalStateException e) {
        return ApiErrorDto.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Illegal State")
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return ApiErrorDto.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Method Argument Not Valid")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        return ApiErrorDto.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason(e.getParameterType() + " " + e.getParameterName())
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorDto handleConstraintViolationException(final ConstraintViolationException e) {
        return ApiErrorDto.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Constraint Violation")
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorDto handleDbConstraintViolationException(final DataIntegrityViolationException e) {
        return ApiErrorDto.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("DB Constraint Violation")
                .status(HttpStatus.CONFLICT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorDto handleServerError(final Throwable e) {
        return ApiErrorDto.builder()
                .errors(Collections.singleton(Arrays.toString(e.getStackTrace())))
                .message(e.getMessage())
                .reason("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
