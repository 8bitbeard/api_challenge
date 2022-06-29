package br.com.itau.challenge.dtos.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CardsResponseDTO {

    private List<CardResponseDTO> cards = new ArrayList<>();

    public void addCard(CardResponseDTO card) {
        this.cards.add(card);
    }
}
