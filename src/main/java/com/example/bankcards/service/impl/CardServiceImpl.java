package com.example.bankcards.service.impl;

import com.example.bankcards.dto.card.CardBalanceDto;
import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.CardShortDto;
import com.example.bankcards.dto.card.NewCardDto;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.CardStatus;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ConflictException;
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
    public Collection<CardDto> getAllCards(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        Collection<CardDto> cardDtos = cardMapper.convertToDtoCollection(
                cardRepository.findAll(pageRequest).getContent());
        log.info("Запрос карт - {}", cardDtos);
        return cardDtos;
    }

    @Override
    public Collection<CardShortDto> getAllCards(long userId, int from, int size) {
        checkUserExists(userId);
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        Collection<CardShortDto> cardDtos = cardMapper.convertToShortDtoCollection(
                cardRepository.findAllByOwnerId(userId, pageRequest).getContent());
        log.info("Запрос карт - {} пользователем id = {}", cardDtos, userId);
        return cardDtos;
    }

    @Override
    public CardBalanceDto getCardBalance(long userId, long cardId) {
        User user = getUserByIdOrElseThrow(userId);
        Card card = getCardByIdOrElseThrow(cardId);
        throwIfUserIsNotCardOwner(card, user);
        log.info("Запрос баланса пользователя = {} карты = {}", user.getId(), card.getId());
        return cardMapper.convertToBalanceDto(card);
    }

    @Override
    @Transactional
    public CardDto addNewCard(NewCardDto newCardDto) {
        User user = getUserByIdOrElseThrow(newCardDto.getOwnerId());
        Card card = cardMapper.convertNewCardDtoToEntity(newCardDto);
        card
        return null;
    }

    @Override
    @Transactional
    public void requestBlockCard(long userId, long cardId) {
        checkUserExists(userId);
        Card card = getCardByIdOrElseThrow(cardId);
        throwIfCardExpired(card);
        throwIfCardBlocked(card);

        card.setBlockRequest(true);
        cardRepository.save(card);
        log.info("Карта id = {} в состоянии запроса на удаление", card.getId());
    }

    @Override
    @Transactional
    public CardDto blockCard(long cardId) {
        Card card = getCardByIdOrElseThrow(cardId);
        throwIfCardExpired(card);
        throwIfCardBlocked(card);

        card.setStatus(CardStatus.BLOCKED);
        CardDto cardDto = cardMapper.convertToDto(cardRepository.save(card));
        log.info("Карта заблокирована: {}", cardDto);
        return cardDto;
    }

    @Override
    @Transactional
    public CardDto activateCard(long cardId) {
        Card card = getCardByIdOrElseThrow(cardId);
        throwIfCardExpired(card);

        if (card.getStatus() == CardStatus.ACTIVE) {
            throw new ConflictException("Карта id = " + cardId + " активна");
        }

        card.setStatus(CardStatus.ACTIVE);
        CardDto cardDto = cardMapper.convertToDto(cardRepository.save(card));
        log.info("Карта активирована: {}", cardDto);
        return cardDto;
    }

    @Override
    @Transactional
    public void deleteCard(long cardId) {
        checkCardExists(cardId);
        cardRepository.deleteById(cardId);
        log.info("Карта id = {} удалена", cardId);
    }

    @Transactional
    private void updateCardStatusIfExpired(Card card) {
        log.info("Проверка на просрочку карты.");
        if (card.getStatus() != CardStatus.EXPIRED && YearMonth.now().equals(card.getExpiresAt())) {
            card.setStatus(CardStatus.EXPIRED);
            log.info("Карта id = {} стала просроченной.", card.getId());
        }
    }

    private void throwIfUserIsNotCardOwner(Card card, User user) {
        Long ownerId = card.getOwner().getId();
        if (!ownerId.equals(user.getId())) {
            log.info("Пользователь id = {} не является владельцем карты id = {}", user.getId(), card.getId());
            throw new ConflictException("Пользователь не является владельцем карты");
        }
    }

    private void throwIfCardExpired(Card card) {
        if (card.getStatus() == CardStatus.EXPIRED) {
            throw new ConflictException("Карта id = " + card.getId() + " с истекшим сроком действия");
        }
    }

    private void throwIfCardBlocked(Card card) {
        if (card.getStatus() == CardStatus.BLOCKED) {
            throw new ConflictException("Карта id = " + card.getId() + " заблокирована");
        }
    }

    private void checkCardExists(long cardId) {
        if (!cardRepository.existsById(cardId)) {
            log.info("Карты id = {} не существует", cardId);
            throw new NotFoundException("Карты id = " + cardId + " не существует");
        }
    }

    private Card getCardByIdOrElseThrow(long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> {
            log.info("Карты id = {} не существует", cardId);
            return new NotFoundException("Карты id = " + cardId + " не существует");
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
