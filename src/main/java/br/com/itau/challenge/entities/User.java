package br.com.itau.challenge.entities;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Card> cards;

    public Card addCard() {
        OffsetDateTime nowTime = OffsetDateTime.now();
        Card newCard = new Card();
        newCard.generateCardCode();
        newCard.generateCardNumber();
        newCard.setCreateDate(nowTime);
        newCard.setExpirationDate(nowTime.plus(5, ChronoUnit.YEARS));
        newCard.setUser(this);

        this.getCards().add(newCard);

        return newCard;
    }
}
