package br.com.itau.challenge.config;

import br.com.itau.challenge.dtos.response.CardResponseDTO;
import br.com.itau.challenge.dtos.response.ContestationResponseDTO;
import br.com.itau.challenge.entities.Card;
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
        TypeMap<Contestation, ContestationResponseDTO> contestationPropertyMapper = modelMapper.createTypeMap(Contestation.class, ContestationResponseDTO.class);
        contestationPropertyMapper.addMapping(src -> src.getPurchase().getValue(), ContestationResponseDTO::setValue);
        contestationPropertyMapper.addMapping(src -> src.getPurchase().getStoreName(), ContestationResponseDTO::setStoreName);
        contestationPropertyMapper.addMapping(src -> src.getPurchase().getCreateDate(), ContestationResponseDTO::setPurchaseDate);
        contestationPropertyMapper.addMapping(src -> src.getPurchase().getPurchaseType(), ContestationResponseDTO::setPurchaseType);

        TypeMap<Card, CardResponseDTO> cardPropertyMapper = modelMapper.createTypeMap(Card.class, CardResponseDTO.class);
        cardPropertyMapper.addMapping(src -> src.getUser().getName(), CardResponseDTO::setUserName);

        return modelMapper;
    }
}
