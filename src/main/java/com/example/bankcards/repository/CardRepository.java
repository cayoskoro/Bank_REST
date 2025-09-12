package com.example.bankcards.repository;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.Collection;

public interface CardRepository extends JpaRepository<Card, Long> {
    public Page<Card> findAllByOwnerId(long ownerId, Pageable pageable);

    public Collection<Card> findAllByStatusNotAndExpiresAtLessThanEqual(CardStatus cardStatus, YearMonth yearMonth);
}
