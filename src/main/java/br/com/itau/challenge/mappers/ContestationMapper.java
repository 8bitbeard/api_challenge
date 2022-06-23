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
        TypeMap<Contestation, ContestationResponseDTO> propertyMapper = modelMapper.createTypeMap(Contestation.class, ContestationResponseDTO.class);
        propertyMapper.addMapping(src -> src.getPurchase().getValue(), ContestationResponseDTO::setValue);
        propertyMapper.addMapping(src -> src.getPurchase().getStoreName(), ContestationResponseDTO::setStoreName);
        propertyMapper.addMapping(src -> src.getPurchase().getCreateDate(), ContestationResponseDTO::setPurchaseDate);
        propertyMapper.addMapping(src -> src.getPurchase().getPurchaseType(), ContestationResponseDTO::setPurchaseType);

        return modelMapper.map(contestation, ContestationResponseDTO.class);
    }

    public List<ContestationResponseDTO> toCollectionDto(List<Contestation> contestations) {
        return contestations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
