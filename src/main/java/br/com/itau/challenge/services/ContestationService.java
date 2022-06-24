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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                .orElseThrow(() -> new UserNotFoundException());
        Card card = cardRepository.findByUserIdAndId(user.getId(), contestationRequestDTO.getCardId())
                .orElseThrow(() -> new UserDoesNotHaveCardException("O usuário logado não possui o cartão informado!"));
        Purchase purchase = purchaseRepository.findByCardIdAndId(card.getId(), contestationRequestDTO.getPurchaseId())
                .orElseThrow(() -> new PurchaseNotFoundException("O usuário logado não possui nenhuma compra com este id!"));

        purchase.setContested(true);
        purchaseRepository.save(purchase);

        Contestation newContestation = new Contestation();
        newContestation.setPurchase(purchase);
        newContestation.setContestationDate(OffsetDateTime.now());
        newContestation.generateProtocolNumber();

        return contestationRepository.save(newContestation);
    }

    public Contestation findById(String userEmail, UUID contestationId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException());
        Contestation contestation = contestationRepository.findById(contestationId)
                .orElseThrow(() -> new ContestationNotFoundException("O usuário logado não possui nenhuma contestação com o id informado!"));

        if(contestation.getPurchase().getCard().getUser().getId() != user.getId()) {
            new ContestationNotFoundException("O usuário logado não possui nenhuma contestação com o id informado!");
        }

        return contestation;
    }

    public List<Contestation> findByCardId(String userEmail, UUID cardId) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException());
        Card userCard = cardRepository.findByUserIdAndId(user.getId(), cardId)
                .orElseThrow(() -> new UserDoesNotHaveCardException("O usuário logado não possui nenhum cartão com o id informado!"));;

        List<Contestation> contestations = new ArrayList<>();
        List<Purchase> purchases = purchaseRepository.findByCardIdAndIsContested(userCard.getId(), true);
        purchases.forEach(purchase -> {
            Contestation contestation = contestationRepository.findByPurchaseId(purchase.getId());
            contestations.add(contestation);
        });

        return contestations;
    }
}
