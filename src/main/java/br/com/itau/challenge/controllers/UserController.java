package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.request.UserRequestDTO;
import br.com.itau.challenge.dtos.response.CardResponseDTO;
import br.com.itau.challenge.dtos.response.UserResponseDTO;
import br.com.itau.challenge.entities.Card;
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
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController implements UserApi {

    private final UserService userService;
    private final CardService cardService;
    private final UserMapper userMapper;
    private final CardMapper cardMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDTO> listUsers() {
        List<User> users = userService.list();
        return userMapper.toCollectionDto(users);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        User userData = userMapper.toEntity(userRequestDTO);
        User newUser = userService.create(userData);

        return userMapper.toDto(newUser);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO sessionUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.find(email);

        return userMapper.toDto(user);
    }

    @GetMapping("/{userId}/cards")
    @ResponseStatus(HttpStatus.OK)
    public List<CardResponseDTO> listCards(@PathVariable UUID userId) {
        List<Card> cards = cardService.listByUserId(userId);
        return cardMapper.toCollectionDto(cards);
    }
}
