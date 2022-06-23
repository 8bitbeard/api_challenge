package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.response.CardResponseDTO;
import br.com.itau.challenge.dtos.response.ContestationResponseDTO;
import br.com.itau.challenge.dtos.response.PurchaseResponseDTO;
import br.com.itau.challenge.entities.Card;
import br.com.itau.challenge.entities.Contestation;
import br.com.itau.challenge.entities.Purchase;
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

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cards")
public class CardController implements CardsApi {

    private final CardService cardService;
    private final PurchaseService purchaseService;
    private final ContestationService contestationService;
    private final CardMapper cardMapper;
    private final PurchaseMapper purchaseMapper;
    private final ContestationMapper contestationMapper;

    @PostMapping("/generate")
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
    public List<PurchaseResponseDTO> listPurchases(@PathVariable UUID cardId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Purchase> purchases = purchaseService.findByCardId(userEmail, cardId);

        return purchaseMapper.toCollectionDto(purchases);
    }

    @GetMapping("{cardId}/purchases/contestations")
    @ResponseStatus(HttpStatus.OK)
    public List<ContestationResponseDTO> listContestations(@PathVariable UUID cardId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Contestation> contestations = contestationService.findByCardId(userEmail, cardId);

        return contestationMapper.toCollectionDto(contestations);
    }
}
