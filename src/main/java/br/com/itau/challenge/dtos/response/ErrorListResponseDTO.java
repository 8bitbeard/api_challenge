package br.com.itau.challenge.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorListResponseDTO {
    private String status;
    private String message;
    private List<Field> fields;

    @AllArgsConstructor
    @Data
    public static class Field {
        private String name;
        private String message;
    }
}
