package br.com.itau.challenge.mappers;

import br.com.itau.challenge.dtos.response.PurchaseResponseDTO;
import br.com.itau.challenge.entities.Purchase;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PurchaseMapper {

    private final ModelMapper modelMapper;

    public PurchaseResponseDTO toDto(Purchase purchase) {
        return modelMapper.map(purchase, PurchaseResponseDTO.class);
    }

}
