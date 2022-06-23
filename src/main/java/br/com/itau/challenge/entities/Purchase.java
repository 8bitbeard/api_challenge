package br.com.itau.challenge.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "purchases")
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private OffsetDateTime createDate;

    @Column(nullable = false)
    private String storeName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseType purchaseType;

    @Column(nullable = false)
    private boolean isContested;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

}
