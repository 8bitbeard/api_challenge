package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.request.UserRequestDTO;
import br.com.itau.challenge.dtos.response.CardsResponseDTO;
import br.com.itau.challenge.dtos.response.UserResponseDTO;
import br.com.itau.challenge.dtos.response.UsersResponseDTO;
import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.mappers.CardMapper;
import br.com.itau.challenge.mappers.UserMapper;
import br.com.itau.challenge.services.CardService;
import br.com.itau.challenge.services.UserService;
import br.com.itau.challenge.swagger.UserApi;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController implements UserApi {

    private final UserService userService;
    private final CardService cardService;
    private final UserMapper userMapper;
    private final CardMapper cardMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UsersResponseDTO listUsers() {
        UsersResponseDTO users = new UsersResponseDTO();
        userService.list().forEach(user -> users.addUser(userMapper.toDto(user)));

        return users;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        User userData = userMapper.toEntity(userRequestDTO);
        User newUser = userService.create(userData);

        return userMapper.toDto(newUser);
    }

    @GetMapping("/{userId}/cards")
    @ResponseStatus(HttpStatus.OK)
    public CardsResponseDTO listCards(@PathVariable UUID userId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        CardsResponseDTO cards = new CardsResponseDTO();
        cardService.listByUserId(userEmail, userId).forEach(card -> cards.addCard(cardMapper.toDto(card)));

        return cards;
    }
}
