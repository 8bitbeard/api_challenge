package br.com.itau.challenge.dtos.response;

import br.com.itau.challenge.entities.PurchaseType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ContestationResponseDTO {
    private UUID id;
    private BigDecimal value;
    private String storeName;
    private OffsetDateTime purchaseDate;
    private PurchaseType purchaseType;
    private OffsetDateTime contestationDate;
    private String protocolNumber;
}
