package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.request.LoginRequestDTO;
import br.com.itau.challenge.dtos.response.LoginResponseDTO;
import br.com.itau.challenge.dtos.response.UserResponseDTO;
import br.com.itau.challenge.entities.User;
import br.com.itau.challenge.mappers.UserMapper;
import br.com.itau.challenge.services.AuthenticationService;
import br.com.itau.challenge.services.CardService;
import br.com.itau.challenge.services.UserService;
import br.com.itau.challenge.swagger.AuthenticationApi;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController implements AuthenticationApi {

    private AuthenticationService authenticationService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword());

        String token = authenticationService.authenticate(authenticationToken);
        LoginResponseDTO tokenResponse = new LoginResponseDTO();
        tokenResponse.setAccessToken(token);

        return tokenResponse;
    }

    @GetMapping("/users/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO sessionUser() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.find(userEmail);

        return userMapper.toDto(user);
    }
}
