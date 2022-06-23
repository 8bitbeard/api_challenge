package br.com.itau.challenge.dtos.response;

import br.com.itau.challenge.entities.PurchaseType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class PurchaseResponseDTO {
    private UUID id;
    private BigDecimal value;
    private OffsetDateTime createDate;
    private String storeName;
    private PurchaseType purchaseType;
}
