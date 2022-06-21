package br.com.itau.challenge.services;

import br.com.itau.challenge.entities.Card;
import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.exceptions.UserAlreadyHaveCardException;
import br.com.itau.challenge.exceptions.UserDoesNotHaveCardException;
import br.com.itau.challenge.exceptions.UserNotFoundException;
import br.com.itau.challenge.repositories.CardRepository;
import br.com.itau.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public Card create(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));
        Optional<Card> userCard = cardRepository.findByUserId(user.getId());

        if(userCard.isPresent()) {
            throw new UserAlreadyHaveCardException("O usuário logado já possui um cartão cadastrado!");
        }

        OffsetDateTime nowTime = OffsetDateTime.now();
        Card newCard = new Card();
        newCard.generateCardCode();
        newCard.generateCardNumber();
        newCard.setCreateDate(nowTime);
        newCard.setExpirationDate(nowTime.plus(5, ChronoUnit.YEARS));
        newCard.setUser(user);

        return cardRepository.save(newCard);
    }

    public Card find(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));
        Card userCard = cardRepository.findByUserId(user.getId()).orElseThrow(() -> new UserDoesNotHaveCardException("O usuário logado não possui nenhum cartão cadastrado!"));;

        return userCard;
    }
}
