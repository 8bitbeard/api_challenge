package br.com.itau.challenge.exceptions.handler;

import br.com.itau.challenge.dtos.response.ErrorResponseDTO;
import br.com.itau.challenge.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public Collection<ErrorResponseDTO> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(violation -> ErrorResponseDTO.as(
                                messageSource.getMessage(violation, LocaleContextHolder.getLocale()))
                        .tag(simpleKey(violation))).collect(Collectors.toList());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Collection<ErrorResponseDTO> handleConstraintViolationException(
            ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .map(violation -> ErrorResponseDTO.as(violation.getMessage())
                        .tag(violation.getPropertyPath().toString())).collect(Collectors.toList());
    }

    @ExceptionHandler(ContestationNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleContestationNotFound(ContestationNotFoundException exception) {
        return ErrorResponseDTO.as(message("ContestationNotFoundException.message"));
    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handlePurchaseNotFound(PurchaseNotFoundException exception) {
        return ErrorResponseDTO.as(message("PurchaseNotFoundException.message"));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorResponseDTO handleUserAlreadyExists(UserAlreadyExistsException exception) {
        return ErrorResponseDTO.as(message("UserAlreadyExistsException.message"));
    }

    @ExceptionHandler(UserDoesNotHaveCardException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorResponseDTO handleUserDoesNotHaveCard(UserDoesNotHaveCardException exception) {
        return ErrorResponseDTO.as(message("UserDoesNotHaveCardException.message"));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorResponseDTO handleUserNotFound(UserNotFoundException exception) {
        return ErrorResponseDTO.as(message("UserNotFoundException.message"));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleInternalServerError(Exception exception) {
        return ErrorResponseDTO.as(message("UserNotFoundException.message"));
    }

    private String simpleKey(ObjectError violation) {
        return violation instanceof FieldError ? ((FieldError) violation).getField()
                : violation.getObjectName();
    }

    private String message(String code, Object... params) {
        return messageSource.getMessage(code, params, LocaleContextHolder.getLocale());
    }

}
