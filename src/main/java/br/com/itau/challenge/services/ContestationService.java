package br.com.itau.challenge.services;

import br.com.itau.challenge.dtos.request.ContestationRequestDTO;
import br.com.itau.challenge.entities.Card;
import br.com.itau.challenge.entities.Contestation;
import br.com.itau.challenge.entities.Purchase;
import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.exceptions.*;
import br.com.itau.challenge.repositories.CardRepository;
import br.com.itau.challenge.repositories.ContestationRepository;
import br.com.itau.challenge.repositories.PurchaseRepository;
import br.com.itau.challenge.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@AllArgsConstructor
@Service
public class ContestationService {

    private final ContestationRepository contestationRepository;
    private final PurchaseRepository purchaseRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Contestation create(String userEmail, ContestationRequestDTO contestationRequestDTO) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        Card card = cardRepository.findByUserIdAndId(user.getId(), contestationRequestDTO.getCardId())
                .orElseThrow(UserDoesNotHaveCardException::new);
        Purchase purchase = purchaseRepository.findByCardIdAndId(card.getId(), contestationRequestDTO.getPurchaseId())
                .orElseThrow(PurchaseNotFoundException::new);

        if(purchase.isContested()) {
            log.error("The purchase {} was already contestated by the user {}", purchase.getId(), user.getEmail());
            throw new PurchaseAlreadyContestedException();
        }

        purchase.setContested(true);
        purchaseRepository.save(purchase);

        log.info("Creating a contestation for the purchase {}", purchase.getId());

        Contestation newContestation = new Contestation();
        newContestation.setPurchase(purchase);
        newContestation.setContestationDate(OffsetDateTime.now());
        newContestation.generateProtocolNumber();

        return contestationRepository.save(newContestation);
    }

    public Contestation findById(String userEmail, UUID contestationId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        Contestation contestation = contestationRepository.findById(contestationId)
                .orElseThrow(ContestationNotFoundException::new);

        if(contestation.getPurchase().getCard().getUser().getId() != user.getId()) {
            log.error("The contestation by id {} wasn't found for the user {}", contestation.getId(), user.getEmail());
            throw new ContestationNotFoundException();
        }

        log.info("Finding the contestation by id for the user {}", user.getEmail());
        return contestation;
    }

    public List<Contestation> findByCardId(String userEmail, UUID cardId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
        Card userCard = cardRepository.findByUserIdAndId(user.getId(), cardId)
                .orElseThrow(UserDoesNotHaveCardException::new);;

        List<Contestation> contestations = new ArrayList<>();
        List<Purchase> purchases = purchaseRepository.findByCardIdAndIsContested(userCard.getId(), true);
        purchases.forEach(purchase -> {
            Contestation contestation = contestationRepository.findByPurchaseId(purchase.getId());
            contestations.add(contestation);
        });

        log.info("Listing the contestations for the user {}", user.getEmail());

        return contestations;
    }
}
