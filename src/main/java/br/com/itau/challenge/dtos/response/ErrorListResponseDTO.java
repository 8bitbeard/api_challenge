package br.com.itau.challenge.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorListResponseDTO {
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
