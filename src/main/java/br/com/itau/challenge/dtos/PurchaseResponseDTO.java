package br.com.itau.challenge.dtos;

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
}
