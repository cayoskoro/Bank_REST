package com.example.bankcards.service;

import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardExpirationService {
    private final CardRepository cardRepository;

    @Scheduled(cron = "0 0 1 1 * *")
    @Transactional
    public void updateCardsWithExpiredDate(Card card) {
        log.info("Плановое обновление всех карт с истекшим expiresAt в состояние status = EXPIRED началось.");
        YearMonth currentMonth = YearMonth.now();
        Collection<Card> cards = cardRepository.findAllByStatusNotAndExpiresAtLessThanEqual(CardStatus.EXPIRED,
                currentMonth);
        cards.forEach(x -> {
            x.setStatus(CardStatus.EXPIRED);
            log.info("Карта id = {} стала просроченной.", x.getId());
        });
        cardRepository.saveAll(cards);
        log.info("Плановое обновление всех карт с истекшим expiresAt в состояние status = EXPIRED завершено.");
    }
}
