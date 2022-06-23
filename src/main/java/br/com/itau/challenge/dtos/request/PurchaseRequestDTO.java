package br.com.itau.challenge.dtos.request;

import br.com.itau.challenge.entities.PurchaseType;
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

    @NotNull
    private PurchaseType purchaseType;
}
