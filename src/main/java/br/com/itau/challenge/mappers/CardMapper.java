package br.com.itau.challenge.mappers;

import br.com.itau.challenge.dtos.response.CardResponseDTO;
import br.com.itau.challenge.entities.Card;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CardMapper {

    private final ModelMapper modelMapper;

    public CardResponseDTO toDto(Card card) {
        return modelMapper.map(card, CardResponseDTO.class);
    }

    public List<CardResponseDTO> toCollectionDto(List<Card> cards) {
        return cards.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
