package br.com.itau.challenge.mappers;

import br.com.itau.challenge.dtos.PurchaseResponseDTO;
import br.com.itau.challenge.entities.Purchase;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PurchaseMapper {

    private final ModelMapper modelMapper;

    public PurchaseResponseDTO toDto(Purchase purchase) {
        return modelMapper.map(purchase, PurchaseResponseDTO.class);
    }

    public List<PurchaseResponseDTO> toCollectionDto(List<Purchase> purchases) {
        return purchases.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
