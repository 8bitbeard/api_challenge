package br.com.itau.challenge.swagger;

import br.com.itau.challenge.dtos.request.PurchaseRequestDTO;
import br.com.itau.challenge.dtos.response.PurchaseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "Purchases")
public interface PurchaseApi {

    @Operation(summary = "Create a new purchase.")
    PurchaseResponseDTO createPurchase(@RequestBody @Valid PurchaseRequestDTO purchaseRequestDTO);

    @Operation(summary = "Display the details of a specific purchase.")
    PurchaseResponseDTO getPurchase(@PathVariable UUID purchaseId);
}
