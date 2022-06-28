package br.com.itau.challenge.services;

import br.com.itau.challenge.entities.Card;
import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.exceptions.UserAlreadyHaveCardException;
import br.com.itau.challenge.exceptions.UserDoesNotHaveCardException;
import br.com.itau.challenge.exceptions.UserNotFoundException;
import br.com.itau.challenge.repositories.CardRepository;
import br.com.itau.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class CardService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public Card create(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        OffsetDateTime nowTime = OffsetDateTime.now();
        Card newCard = new Card();
        newCard.generateCardCode();
        newCard.generateCardNumber();
        newCard.setCreateDate(nowTime);
        newCard.setExpirationDate(nowTime.plus(5, ChronoUnit.YEARS));
        newCard.setUser(user);

        log.info("Generating a new card with number {} for the user {}", newCard.getNumber(), user.getEmail());

        return cardRepository.save(newCard);
    }

    public Card findById(String userEmail, UUID cardId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Card userCard = cardRepository.findByUserIdAndId(user.getId(), cardId).orElseThrow(UserDoesNotHaveCardException::new);

        log.info("Getting user {} card by id", user.getEmail());

        return userCard;
    }

    public List<Card> listByUserId(UUID userId) {

        log.info("Listing all cards from user {} by id", userId);

        return cardRepository.findByUserId(userId);
    }
}
