package br.com.itau.challenge.controllers;

import br.com.itau.challenge.dtos.request.PurchaseRequestDTO;
import br.com.itau.challenge.dtos.response.PurchaseResponseDTO;
import br.com.itau.challenge.entities.Purchase;
import br.com.itau.challenge.mappers.PurchaseMapper;
import br.com.itau.challenge.services.PurchaseService;
import br.com.itau.challenge.swagger.PurchaseApi;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/purchases")
public class PurchaseController implements PurchaseApi {

    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResponseDTO createPurchase(@RequestBody @Valid PurchaseRequestDTO purchaseRequestDTO) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Purchase newPurchase = purchaseService.create(userEmail, purchaseRequestDTO);

        return purchaseMapper.toDto(newPurchase);
    }

    @GetMapping("/{purchaseId}")
    @ResponseStatus(HttpStatus.OK)
    public PurchaseResponseDTO getPurchase(@PathVariable UUID purchaseId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Purchase purchase = purchaseService.findById(userEmail, purchaseId);

        return purchaseMapper.toDto(purchase);
    }
}
