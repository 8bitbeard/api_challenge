package br.com.itau.challenge.mappers;

import br.com.itau.challenge.dtos.CardResponseDTO;
import br.com.itau.challenge.entities.Card;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CardMapper {

    private final ModelMapper modelMapper;

    public CardResponseDTO toDto(Card card) {
        return modelMapper.map(card, CardResponseDTO.class);
    }
}
