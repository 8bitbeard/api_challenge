package br.com.itau.challenge.swagger;

import br.com.itau.challenge.dtos.request.UserRequestDTO;
import br.com.itau.challenge.dtos.response.CardsResponseDTO;
import br.com.itau.challenge.dtos.response.UserResponseDTO;
import br.com.itau.challenge.dtos.response.UsersResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "Users")
public interface UserApi {

    @Operation(summary = "List all users")
    @SecurityRequirements(value = {})
    UsersResponseDTO listUsers();

    @Operation(summary = "Create a new user")
    @SecurityRequirements(value = {})
    UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userRequestDTO);

    @Operation(summary = "Show the information of the logged in user")
    UserResponseDTO sessionUser();

    @Operation(summary = "List all the cards owned by a specific user")
    CardsResponseDTO listCards(@PathVariable UUID userId);

}
