package br.com.itau.challenge.swagger;

import br.com.itau.challenge.dtos.response.CardResponseDTO;
import br.com.itau.challenge.dtos.response.ContestationResponseDTO;
import br.com.itau.challenge.dtos.response.PurchaseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Tag(name = "Cards")
public interface CardsApi {

    @Operation(summary = "Creates a new card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    CardResponseDTO create();

    @Operation(summary = "Get a specific card informations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    CardResponseDTO getCard(@PathVariable UUID cardId);

    List<PurchaseResponseDTO> listPurchases(@PathVariable UUID cardId);

    List<ContestationResponseDTO> listContestations(@PathVariable UUID cardId);
}
