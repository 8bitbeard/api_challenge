package br.com.itau.challenge.services;

import br.com.itau.challenge.dtos.PurchaseRequestDTO;
import br.com.itau.challenge.entities.Card;
import br.com.itau.challenge.entities.Purchase;
import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.exceptions.UserDoesNotHaveCardException;
import br.com.itau.challenge.exceptions.UserNotFoundException;
import br.com.itau.challenge.repositories.CardRepository;
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

    @Transactional
    public Purchase create(String userEmail, PurchaseRequestDTO purchaseRequestDTO) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado!"));
        Card card = cardRepository.findById(purchaseRequestDTO.getCardId()).orElseThrow(() -> new UserDoesNotHaveCardException("O usuário logado não possui o cartão informado!"));

        Purchase purchaseData = new Purchase();
        purchaseData.setCreateDate(OffsetDateTime.now());
        purchaseData.setStoreName(purchaseRequestDTO.getStoreName());
        purchaseData.setValue(purchaseRequestDTO.getValue());
        purchaseData.setCard(card);
        purchaseData.setUser(user);

        return purchaseRepository.save(purchaseData);
    }

    public List<Purchase> listFromCard(UUID cardId) {
        return purchaseRepository.findByCardId(cardId);
    }
}
