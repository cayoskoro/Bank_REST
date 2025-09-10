package com.example.bankcards.service.impl;

import com.example.bankcards.dto.card.CardResponseDto;
import com.example.bankcards.dto.card.NewCardRequestDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.CardExpiredException;
import com.example.bankcards.exception.CardStatusConflictException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;

    @Override
    public Collection<CardResponseDto> getAllCards(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        return cardMapper.convertToDtoCollection(cardRepository.findAll(pageRequest).getContent());
    }

    @Override
    public Collection<CardResponseDto> getAllCards(long userId, int from, int size) {
        checkUserExists(userId);
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        return cardMapper.convertToDtoCollection(cardRepository.findAllByOwnerId(userId, pageRequest).getContent());
    }

    @Override
    @Transactional
    public CardResponseDto addNewCard(NewCardRequestDto newCardRequestDto) {
        User user = getUserByIdOrElseThrow(newCardRequestDto.getOwnerId());

        return null;
    }

    @Override
    @Transactional
    public CardResponseDto blockCard(long cardId) {
        Card card = getCardByIdOrElseThrow(cardId);
        throwIfCardExpired(card);

        if (card.getStatus() == CardStatus.BLOCKED) {
            throw new CardStatusConflictException("Карта по cardId = " + cardId + " уже была заблокирована");
        }
        card.setStatus(CardStatus.BLOCKED);

        CardResponseDto cardResponseDto = cardMapper.convertToDto(cardRepository.save(card));
        log.info("Карта заблокирована: {}", cardResponseDto);
        return cardResponseDto;
    }

    @Override
    @Transactional
    public CardResponseDto activateCard(long cardId) {
        Card card = getCardByIdOrElseThrow(cardId);
        throwIfCardExpired(card);

        if (card.getStatus() == CardStatus.ACTIVE) {
            throw new CardStatusConflictException("Карта по cardId = " + cardId + " уже была активирована");
        }
        card.setStatus(CardStatus.ACTIVE);

        CardResponseDto cardResponseDto = cardMapper.convertToDto(cardRepository.save(card));
        log.info("Карта активирована: {}", cardResponseDto);
        return cardResponseDto;
    }

    @Override
    @Transactional
    public void deleteCard(long cardId) {
        checkCardExists(cardId);
        cardRepository.deleteById(cardId);
        log.info("Карта с cardId = {} удалена", cardId);
    }

    @Transactional
    private void updateCardStatusIfExpired(Card card) {
        log.info("Проверка на просрочку карты.");
        if (card.getStatus() != CardStatus.EXPIRED && YearMonth.now().equals(card.getExpiresAt())) {
            card.setStatus(CardStatus.EXPIRED);
            log.info("Карта cardId = {} стала просроченной.", card.getId());
        }
    }

    private void throwIfCardExpired(Card card) {
        if (card.getStatus() == CardStatus.EXPIRED) {
            throw new CardExpiredException("У карты по cardId = " + card.getId() + " истек срок действия");
        }
    }

    private void checkCardExists(long cardId) {
        if (!cardRepository.existsById(cardId)) {
            log.info("Карты с cardId = {} не существует", cardId);
            throw new NotFoundException("Карты с cardId = " + cardId + " не существует");
        }
    }

    private Card getCardByIdOrElseThrow(long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> {
            log.info("Карты с cardId = {} не существует", cardId);
            return new NotFoundException("Карты с cardId = " + cardId + " не существует");
        });
    }

    private void checkUserExists(long userId) {
        if (!userRepository.existsById(userId)) {
            log.info("Пользователя id = {} не существует", userId);
            throw new NotFoundException("Пользователя id = " + userId + " не существует");
        }
    }

    private User getUserByIdOrElseThrow(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.info("Пользователя id = {} не существует", userId);
            return new NotFoundException("Пользователя id = " + userId + " не существует");
        });
    }
}
