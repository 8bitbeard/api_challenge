package br.com.itau.challenge.exceptions.handler;

import br.com.itau.challenge.dtos.response.ErrorListResponseDTO;
import br.com.itau.challenge.dtos.response.ErrorResponseDTO;
import br.com.itau.challenge.exceptions.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Pattern ENUM_MSG = Pattern.compile("values accepted for Enum class: \\[(.*?)\\]");
    private static final Pattern UUID_MSG = Pattern.compile("UUID has to be represented by standard 36-char representation");
    private static final Pattern UUID_PATH_MSG = Pattern.compile("Invalid UUID string");

    private final MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListResponseDTO handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        List<ErrorListResponseDTO.Field> fields = new ArrayList<>();

        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            fields.add(new ErrorListResponseDTO.Field(name, message));
        }

        ErrorListResponseDTO error = new ErrorListResponseDTO();
        error.setTime(OffsetDateTime.now());
        error.setMessage(message("MethodArgumentNotValidException.message"));
        error.setFields(fields);

        return error;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorListResponseDTO handleConstraintViolationException(ConstraintViolationException exception) {
        List<ErrorListResponseDTO.Field> fields = exception.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorListResponseDTO.Field(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());

        ErrorListResponseDTO error = new ErrorListResponseDTO();
        error.setTime(OffsetDateTime.now());
        error.setMessage(message("ConstraintViolationException.message"));
        error.setFields(fields);

        return error;
    }

    @ExceptionHandler(ContestationNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleContestationNotFound(ContestationNotFoundException exception) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setTime(OffsetDateTime.now());
        error.setMessage(message("ContestationNotFoundException.message"));

        return error;
    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handlePurchaseNotFound(PurchaseNotFoundException exception) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setTime(OffsetDateTime.now());
        error.setMessage(message("PurchaseNotFoundException.message"));

        return error;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorResponseDTO handleUserAlreadyExists(UserAlreadyExistsException exception) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setTime(OffsetDateTime.now());
        error.setMessage(message("UserAlreadyExistsException.message"));

        return error;
    }

    @ExceptionHandler(UserDoesNotHaveCardException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleUserDoesNotHaveCard(UserDoesNotHaveCardException exception) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setTime(OffsetDateTime.now());
        error.setMessage(message("UserDoesNotHaveCardException.message"));

        return error;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ErrorResponseDTO handleUserNotFound(UserNotFoundException exception) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setTime(OffsetDateTime.now());
        error.setMessage(message("UserNotFoundException.message"));

        return error;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setTime(OffsetDateTime.now());
        if (exception.getCause() != null && exception.getCause() instanceof IllegalArgumentException) {
            System.out.println(exception.getCause().getMessage());
            Matcher match_uuid = UUID_PATH_MSG.matcher(exception.getCause().getMessage());
            if (match_uuid.find()) {
                error.setMessage(message("UuidInvalidValue.message"));
                return error;
            }
        }

        error.setMessage(exception.getMessage());
        return error;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleInternalServerError(Exception exception) {
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setTime(OffsetDateTime.now());
        error.setMessage(exception.getMessage());

        return error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponseDTO handleJsonErrors(HttpMessageNotReadableException exception){
        ErrorResponseDTO error = new ErrorResponseDTO();
        error.setTime(OffsetDateTime.now());


        if (exception.getCause() != null && exception.getCause() instanceof InvalidFormatException) {
            Matcher match_enum = ENUM_MSG.matcher(exception.getCause().getMessage());
            Matcher match_uuid = UUID_MSG.matcher(exception.getCause().getMessage());
            if (match_enum.find()) {
                error.setMessage(message("EnumInvalidValues.message") + match_enum.group(1));
                return error;
            } else if (match_uuid.find()) {
                error.setMessage(message("UuidInvalidValue.message"));
                return error;
            }
        }

        error.setMessage(exception.getMessage());
        return error;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ErrorResponseDTO handleInvalidUuid(MethodArgumentTypeMismatchException exception){
//        ErrorResponseDTO error = new ErrorResponseDTO();
//        error.setTime(OffsetDateTime.now());
//
//        if (exception.getCause() != null && exception.getCause() instanceof InvalidFormatException) {
//            Matcher match = ENUM_MSG.matcher(exception.getCause().getMessage());
//            if (match.find()) {
//                error.setMessage(message("UuidInvalidValue.message") + match.group(1));
//                return error;
//            }
//        }
//
//        error.setMessage(exception.getMessage());
//        return error;
//    }

    private String message(String code, Object... params) {
        return messageSource.getMessage(code, params, LocaleContextHolder.getLocale());
    }
}
