package br.com.itau.challenge.dtos.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ContestationsResponseDTO {

    List<ContestationResponseDTO> contestations = new ArrayList<>();

    public void addContestaton(ContestationResponseDTO contestationResponseDTO) {
        this.contestations.add(contestationResponseDTO);
    }
}
