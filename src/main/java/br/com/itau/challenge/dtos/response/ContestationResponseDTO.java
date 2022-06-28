package br.com.itau.challenge.dtos.response;

import br.com.itau.challenge.entities.PurchaseType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ContestationResponseDTO {
    private UUID id;
    private BigDecimal value;

    @JsonProperty
    private String storeName;
    private OffsetDateTime purchaseDate;
    private PurchaseType purchaseType;

    @JsonProperty
    private OffsetDateTime contestationDate;

    @JsonProperty
    private String protocolNumber;
}
