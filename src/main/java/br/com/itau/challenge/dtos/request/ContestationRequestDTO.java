package br.com.itau.challenge.dtos.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ContestationRequestDTO {

    @NotNull
    private UUID cardId;

    @NotNull
    private UUID purchaseId;
}
