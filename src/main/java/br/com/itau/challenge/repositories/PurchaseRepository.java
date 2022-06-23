package br.com.itau.challenge.repositories;

import br.com.itau.challenge.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    List<Purchase> findByCardId(UUID cardId);
    Optional<Purchase> findByCardIdAndId(UUID cardId, UUID id);
//    Optional<Purchase> findByUserIdAndId (UUID userId, UUID id);
    List<Purchase> findByCardIdAndIsContested (UUID cardId, Boolean isContested);
}
