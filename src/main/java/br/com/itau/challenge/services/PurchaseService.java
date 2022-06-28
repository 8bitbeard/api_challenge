package br.com.itau.challenge.services;

import br.com.itau.challenge.dtos.request.PurchaseRequestDTO;
import br.com.itau.challenge.entities.Card;
import br.com.itau.challenge.entities.Contestation;
import br.com.itau.challenge.entities.Purchase;
import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.exceptions.PurchaseNotFoundException;
import br.com.itau.challenge.exceptions.UserDoesNotHaveCardException;
import br.com.itau.challenge.exceptions.UserNotFoundException;
import br.com.itau.challenge.repositories.CardRepository;
import br.com.itau.challenge.repositories.ContestationRepository;
import br.com.itau.challenge.repositories.PurchaseRepository;
import br.com.itau.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class PurchaseService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final PurchaseRepository purchaseRepository;
    private final ContestationRepository contestationRepository;

    @Transactional
    public Purchase create(String userEmail, PurchaseRequestDTO purchaseRequestDTO) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Card card = cardRepository.findById(purchaseRequestDTO.getCardId()).orElseThrow(UserDoesNotHaveCardException::new);

        Purchase purchaseData = new Purchase();
        purchaseData.setCreateDate(OffsetDateTime.now());
        purchaseData.setStoreName(purchaseRequestDTO.getStoreName());
        purchaseData.setValue(purchaseRequestDTO.getValue());
        purchaseData.setPurchaseType(purchaseRequestDTO.getPurchaseType());
        purchaseData.setCard(card);

        log.info("Creating a new purchase for the user {}", user.getId());

        return purchaseRepository.save(purchaseData);
    }

    public Purchase findById(String userEmail, UUID purchaseId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(PurchaseNotFoundException::new);

        if(purchase.getCard().getUser().getId() != user.getId()) {
            log.error("The user {} has no purchases with id {}", user.getId(), purchase.getId());
            throw new PurchaseNotFoundException();
        }

        log.info("Finding a purchase for user {} by id", user.getId());
        return purchase;
    }

    public List<Purchase> findByCardId(String userEmail, UUID cardId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Card userCard = cardRepository.findByUserIdAndId(user.getId(), cardId).orElseThrow(UserDoesNotHaveCardException::new);

        log.info("Listing all purchases by cardId for user {}", user.getName());

        return purchaseRepository.findByCardId(userCard.getId());
    }
}
