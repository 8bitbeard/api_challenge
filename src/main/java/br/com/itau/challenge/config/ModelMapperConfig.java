package br.com.itau.challenge.config;

import br.com.itau.challenge.dtos.response.ContestationResponseDTO;
import br.com.itau.challenge.entities.Contestation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Contestation, ContestationResponseDTO> propertyMapper = modelMapper.createTypeMap(Contestation.class, ContestationResponseDTO.class);
        propertyMapper.addMapping(src -> src.getPurchase().getValue(), ContestationResponseDTO::setValue);
        propertyMapper.addMapping(src -> src.getPurchase().getStoreName(), ContestationResponseDTO::setStoreName);
        propertyMapper.addMapping(src -> src.getPurchase().getCreateDate(), ContestationResponseDTO::setPurchaseDate);
        propertyMapper.addMapping(src -> src.getPurchase().getPurchaseType(), ContestationResponseDTO::setPurchaseType);

        return modelMapper;
    }
}
