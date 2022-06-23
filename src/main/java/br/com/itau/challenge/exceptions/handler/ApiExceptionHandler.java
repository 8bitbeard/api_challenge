package br.com.itau.challenge.exceptions.handler;

import br.com.itau.challenge.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestControllerAdvice
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
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleUserAlreadyExists(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFound(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserAlreadyHaveCardException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleUserAlreadyHaveCard(UserAlreadyHaveCardException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserDoesNotHaveCardException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleDoesNotHaveCardException(UserDoesNotHaveCardException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlePurchaseNotFound(PurchaseNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ContestationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleContestationNotFound(ContestationNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleForbidden(ForbiddenException ex, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        Error error = new Error();
        error.setStatus(status.value());
        error.setTime(OffsetDateTime.now());
        error.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
    }
}
