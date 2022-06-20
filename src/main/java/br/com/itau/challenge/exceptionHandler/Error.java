package br.com.itau.challenge.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Error {
    private Integer status;
    private String message;
    private OffsetDateTime time;
    private List<Field> fields;

    @AllArgsConstructor
    @Data
    public static class Field {
        private String name;
        private String message;
    }
}
