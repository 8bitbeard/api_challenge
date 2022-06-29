package br.com.itau.challenge.dtos.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PurchasesResponseDTO {

    List<PurchaseResponseDTO> purchases = new ArrayList<>();

    public void addPurchase(PurchaseResponseDTO purchaseResponseDTO) {
        this.purchases.add(purchaseResponseDTO);
    }
}
