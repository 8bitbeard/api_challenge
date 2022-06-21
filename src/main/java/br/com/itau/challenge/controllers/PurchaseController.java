package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.PurchaseRequestDTO;
import br.com.itau.challenge.dtos.PurchaseResponseDTO;
import br.com.itau.challenge.entities.Purchase;
import br.com.itau.challenge.mappers.PurchaseMapper;
import br.com.itau.challenge.services.PurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponseDTO create(@RequestBody @Valid PurchaseRequestDTO purchaseRequestDTO) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Purchase newPurchase = purchaseService.create(userEmail, purchaseRequestDTO);

        return purchaseMapper.toDto(newPurchase);
    }
}
