package br.com.itau.challenge.repositories;

import br.com.itau.challenge.entities.Contestation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContestationRepository extends JpaRepository<Contestation, UUID> {
    Contestation findByPurchaseId(UUID contestationId);
}
