package br.com.itau.challenge.exceptions.handler;

import br.com.itau.challenge.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Error.Field> fields = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            fields.add(new Error.Field(name, message));

        }

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente");
        error.setFields(fields);

        return handleExceptionInternal(ex, error, headers, status, request);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserAlreadyHaveCardException.class)
    public ResponseEntity<Object> handleUserAlreadyHaveCard(UserAlreadyHaveCardException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserDoesNotHaveCardException.class)
    public ResponseEntity<Object> handleDoesNotHaveCardException(UserDoesNotHaveCardException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    public ResponseEntity<Object> handlePurchaseNotFound(PurchaseNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ContestationNotFoundException.class)
    public ResponseEntity<Object> handleContestationNotFound(ContestationNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbidden(ForbiddenException ex, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }
}
