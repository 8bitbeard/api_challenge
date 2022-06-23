package br.com.itau.challenge.mappers;

import br.com.itau.challenge.dtos.request.UserRequestDTO;
import br.com.itau.challenge.dtos.response.UserResponseDTO;
import br.com.itau.challenge.entities.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public User toEntity(UserRequestDTO userRequestDTO) {
        return modelMapper.map(userRequestDTO, User.class);
    }

    public List<UserResponseDTO> toCollectionDto(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDTO toDto(User user) {
        return modelMapper.map(user, UserResponseDTO.class);
    }
}
