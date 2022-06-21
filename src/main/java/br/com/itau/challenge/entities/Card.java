package br.com.itau.challenge.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Random;
import java.util.UUID;

@Data
@Entity
@Table(name = "cards")
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String number;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private OffsetDateTime expirationDate;

    @Column(nullable = false)
    private OffsetDateTime createDate;

    public void generateCardNumber() {
        long leftLimit = 1000000000000000L;
        long rightLimit = 9000000000000000L;
        long cardNumber = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
        setNumber(String.valueOf(cardNumber));
    }

    public void generateCardCode() {
        Random rnd = new Random();
        int cardCode = 100 + rnd.nextInt(900);
        setCode(String.valueOf(cardCode));
    }
}
