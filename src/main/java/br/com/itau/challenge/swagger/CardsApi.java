package br.com.itau.challenge.swagger;

import br.com.itau.challenge.dtos.response.CardResponseDTO;
import br.com.itau.challenge.dtos.response.ContestationsResponseDTO;
import br.com.itau.challenge.dtos.response.PurchasesResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Tag(name = "Cards")
public interface CardsApi {

    @Operation(summary = "Creates a new card")
    CardResponseDTO create();

    @Operation(summary = "Get a specific card informations")
    CardResponseDTO getCard(@PathVariable UUID cardId);

    @Operation(summary = "Get all the purchases from a given card")
    PurchasesResponseDTO listPurchases(@PathVariable UUID cardId);

    @Operation(summary = "Get all the contestations from purchases of a specific card")
    ContestationsResponseDTO listContestations(@PathVariable UUID cardId);
}
