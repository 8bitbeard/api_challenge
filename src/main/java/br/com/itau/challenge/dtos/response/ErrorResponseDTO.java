package br.com.itau.challenge.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponseDTO implements Serializable {

    private static final long serialVersionUID = -1L;
    private String error;
    private String errorCode;
    private String errorDescription;

    public ErrorResponseDTO code(String code) {
        this.errorCode = code;
        return this;
    }

    public ErrorResponseDTO tag(String tag) {
        this.error = tag;
        return this;
    }

    public ErrorResponseDTO descripion(String description)  {
        this.errorDescription = description;
        return this;
    }

    public static ErrorResponseDTO as(String description) {
        return new ErrorResponseDTO().descripion(description);
    }
}
