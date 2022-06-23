package br.com.itau.challenge.dtos.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class CardResponseDTO {

    private UUID id;
    private String number;
    private String code;
    private OffsetDateTime expirationDate;
    private UserResponseDTO user;
}
