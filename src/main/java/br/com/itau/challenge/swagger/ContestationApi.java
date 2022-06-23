package br.com.itau.challenge.swagger;

import br.com.itau.challenge.dtos.request.ContestationRequestDTO;
import br.com.itau.challenge.dtos.response.ContestationResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "Contestations")
public interface ContestationApi {

    @Operation(summary = "Create a new contestation.")
    ContestationResponseDTO createContestation(@RequestBody @Valid ContestationRequestDTO contestationRequestDTO);

    @Operation(summary = "Display the details of a specific contestation.")
    ContestationResponseDTO getContestation(@PathVariable UUID contestationId);
}
