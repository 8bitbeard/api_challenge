package br.com.itau.challenge.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PurchaseRequestDTO {

    @NotNull
    private UUID cardId;

    @NotNull
    private BigDecimal value;

    @NotBlank
    @Size(max = 20)
    private String storeName;
}
