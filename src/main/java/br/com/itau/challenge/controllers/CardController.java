package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.response.CardResponseDTO;
import br.com.itau.challenge.dtos.response.ContestationsResponseDTO;
import br.com.itau.challenge.dtos.response.PurchasesResponseDTO;
import br.com.itau.challenge.entities.Card;
import br.com.itau.challenge.mappers.CardMapper;
import br.com.itau.challenge.mappers.ContestationMapper;
import br.com.itau.challenge.mappers.PurchaseMapper;
import br.com.itau.challenge.services.CardService;
import br.com.itau.challenge.services.ContestationService;
import br.com.itau.challenge.services.PurchaseService;
import br.com.itau.challenge.swagger.CardsApi;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/cards")
public class CardController implements CardsApi {

    private final CardService cardService;
    private final PurchaseService purchaseService;
    private final ContestationService contestationService;
    private final CardMapper cardMapper;
    private final PurchaseMapper purchaseMapper;
    private final ContestationMapper contestationMapper;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponseDTO create() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Card newCard = cardService.create(email);

        return cardMapper.toDto(newCard);
    }

    @GetMapping("/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public CardResponseDTO getCard(@PathVariable UUID cardId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Card userCard = cardService.findById(userEmail, cardId);

        return cardMapper.toDto(userCard);
    }

    @GetMapping("/{cardId}/purchases")
    @ResponseStatus(HttpStatus.OK)
    public PurchasesResponseDTO listPurchases(@PathVariable UUID cardId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        PurchasesResponseDTO purchases = new PurchasesResponseDTO();
        purchaseService.findByCardId(userEmail, cardId).forEach(purchase -> purchases.addPurchase(purchaseMapper.toDto(purchase)));

        return purchases;
    }

    @GetMapping("/{cardId}/purchases/contestations")
    @ResponseStatus(HttpStatus.OK)
    public ContestationsResponseDTO listContestations(@PathVariable UUID cardId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        ContestationsResponseDTO contestations = new ContestationsResponseDTO();
        contestationService.findByCardId(userEmail, cardId).forEach(contestation -> contestations.addContestaton(contestationMapper.toDto(contestation)));

        return contestations;
    }
}
