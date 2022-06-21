package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.CardResponseDTO;
import br.com.itau.challenge.entities.Card;
import br.com.itau.challenge.mappers.CardMapper;
import br.com.itau.challenge.services.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;
    private final CardMapper cardMapper;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Card newCard = cardService.create(email);

        return cardMapper.toDto(newCard);
    }
}
