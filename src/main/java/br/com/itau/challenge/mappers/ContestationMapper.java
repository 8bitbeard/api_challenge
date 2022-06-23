package br.com.itau.challenge.mappers;

import br.com.itau.challenge.dtos.response.ContestationResponseDTO;
import br.com.itau.challenge.entities.Contestation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ContestationMapper {

    private final ModelMapper modelMapper;

    public ContestationResponseDTO toDto(Contestation contestation) {
        return modelMapper.map(contestation, ContestationResponseDTO.class);
    }

    public List<ContestationResponseDTO> toCollectionDto(List<Contestation> contestations) {
        return contestations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
