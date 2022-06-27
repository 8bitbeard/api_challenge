package br.com.itau.challenge.dtos.request;

import br.com.itau.challenge.entities.PurchaseType;
import br.com.itau.challenge.validators.PurchaseTypeSubset;
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
    @PurchaseTypeSubset(anyOf = {PurchaseType.ONLINE, PurchaseType.STORE})
    private PurchaseType purchaseType;
}
