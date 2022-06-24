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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PurchaseService {

    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final PurchaseRepository purchaseRepository;
    private final ContestationRepository contestationRepository;

    @Transactional
    public Purchase create(String userEmail, PurchaseRequestDTO purchaseRequestDTO) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException());
        Card card = cardRepository.findById(purchaseRequestDTO.getCardId()).orElseThrow(() -> new UserDoesNotHaveCardException("O usuário logado não possui o cartão informado!"));

        Purchase purchaseData = new Purchase();
        purchaseData.setCreateDate(OffsetDateTime.now());
        purchaseData.setStoreName(purchaseRequestDTO.getStoreName());
        purchaseData.setValue(purchaseRequestDTO.getValue());
        purchaseData.setPurchaseType(purchaseRequestDTO.getPurchaseType());
        purchaseData.setCard(card);

        return purchaseRepository.save(purchaseData);
    }

    public Purchase findById(String userEmail, UUID purchaseId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException());
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new PurchaseNotFoundException("Nenhuma compra com o id informado foi encontrada!"));

        if(purchase.getCard().getUser().getId() != user.getId()) {
            new PurchaseNotFoundException("Nenhuma compra com o id informado foi encontrada!");
        }

        return purchase;
    }

    public List<Purchase> findByCardId(String userEmail, UUID cardId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException());
        Card userCard = cardRepository.findByUserIdAndId(user.getId(), cardId).orElseThrow(() -> new UserDoesNotHaveCardException("O usuário logado não possui nenhum cartão com o id informado!"));;

        return purchaseRepository.findByCardId(userCard.getId());
    }

    @Transactional
    public Contestation createContestation(UUID purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(() -> new PurchaseNotFoundException("There are no purchases with the given id!"));
        purchase.setContested(true);
        purchaseRepository.save(purchase);

        Contestation newContestation = new Contestation();
        newContestation.setPurchase(purchase);
        newContestation.generateProtocolNumber();

        return contestationRepository.save(newContestation);
    }
}
