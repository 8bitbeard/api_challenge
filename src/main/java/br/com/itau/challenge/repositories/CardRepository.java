package br.com.itau.challenge.repositories;

import br.com.itau.challenge.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    List<Card> findByUserId(UUID userId);
    Optional<Card> findByUserIdAndId(UUID userId, UUID cardId);
}
