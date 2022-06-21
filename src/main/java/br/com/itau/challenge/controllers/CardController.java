package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.CardResponseDTO;
import br.com.itau.challenge.dtos.PurchaseResponseDTO;
import br.com.itau.challenge.entities.Card;
import br.com.itau.challenge.entities.Purchase;
import br.com.itau.challenge.mappers.CardMapper;
import br.com.itau.challenge.mappers.PurchaseMapper;
import br.com.itau.challenge.services.CardService;
import br.com.itau.challenge.services.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;
    private final PurchaseService purchaseService;
    private final CardMapper cardMapper;
    private final PurchaseMapper purchaseMapper;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public CardResponseDTO userCard() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Card userCard = cardService.find(email);

        return cardMapper.toDto(userCard);
    }

    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public CardResponseDTO create() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Card newCard = cardService.create(email);

        return cardMapper.toDto(newCard);
    }

    @GetMapping("/{cardId}/purchases")
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseResponseDTO> listPurchases(@PathVariable UUID cardId) {
        List<Purchase> purchases = purchaseService.listFromCard(cardId);

        return purchaseMapper.toCollectionDto(purchases);
    }
}
