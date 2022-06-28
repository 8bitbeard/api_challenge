package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.request.ContestationRequestDTO;
import br.com.itau.challenge.dtos.response.ContestationResponseDTO;
import br.com.itau.challenge.entities.Contestation;
import br.com.itau.challenge.mappers.ContestationMapper;
import br.com.itau.challenge.services.ContestationService;
import br.com.itau.challenge.swagger.ContestationApi;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/contestations")
public class ContestationController implements ContestationApi {

    private final ContestationService contestationService;
    private final ContestationMapper contestationMapper;
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContestationResponseDTO createContestation(@RequestBody @Valid ContestationRequestDTO contestationRequestDTO) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Contestation newContestation = contestationService.create(userEmail, contestationRequestDTO);
        ContestationResponseDTO newContestationDTO = contestationMapper.toDto(newContestation);

        kafkaTemplate.send("contestation-1", newContestationDTO);

        return newContestationDTO;
    }

    @GetMapping("/{contestationId}")
    @ResponseStatus(HttpStatus.OK)
    public ContestationResponseDTO getContestation(@PathVariable UUID contestationId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Contestation contestation = contestationService.findById(userEmail, contestationId);

        return contestationMapper.toDto(contestation);
    }

}
