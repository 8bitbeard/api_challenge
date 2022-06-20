package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.UserRequestDTO;
import br.com.itau.challenge.dtos.UserResponseDTO;
import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.mappers.UserMapper;
import br.com.itau.challenge.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        User userData = userMapper.toEntity(userRequestDTO);
        User newUser = userService.create(userData);

        return userMapper.toDto(newUser);
    }
}
