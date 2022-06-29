package br.com.itau.challenge.mappers;

import br.com.itau.challenge.dtos.response.ContestationResponseDTO;
import br.com.itau.challenge.entities.Contestation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ContestationMapper {

    private final ModelMapper modelMapper;

    public ContestationResponseDTO toDto(Contestation contestation) {
        return modelMapper.map(contestation, ContestationResponseDTO.class);
    }

}
