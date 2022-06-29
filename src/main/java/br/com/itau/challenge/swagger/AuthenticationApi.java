package br.com.itau.challenge.swagger;

import br.com.itau.challenge.dtos.request.LoginRequestDTO;
import br.com.itau.challenge.dtos.response.LoginResponseDTO;
import br.com.itau.challenge.dtos.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication")
public interface AuthenticationApi {

    @Operation(summary = "Log in the user to the application")
    @SecurityRequirements(value = {})
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    @Operation(summary = "Show the information of the logged in user")
    UserResponseDTO sessionUser();
}
