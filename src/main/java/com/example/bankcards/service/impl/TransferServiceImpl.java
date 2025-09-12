package com.example.bankcards.service.impl;

import com.example.bankcards.dto.transfer.NewTransferDto;
import com.example.bankcards.dto.transfer.TransferDto;
import com.example.bankcards.entity.*;
import com.example.bankcards.exception.ConflictException;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.mapper.TransferMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final TransferMapper transferMapper;

    @Override
    @Transactional
    public TransferDto transferInternalFunds(long userId, NewTransferDto newTransferDto) {
        checkUserExists(userId);

        Card fromCard = getCardByIdOrElseThrow(newTransferDto.getFromCard());
        throwIfUserIsNotCardOwner(fromCard, userId);
        throwIfCardIsNotActive(fromCard);

        Card toCard = getCardByIdOrElseThrow(newTransferDto.getToCard());
        throwIfUserIsNotCardOwner(toCard, userId);
        throwIfCardIsNotActive(toCard);

        if (fromCard.getBalance().compareTo(newTransferDto.getAmount()) < 0) {
            log.info("Трансфер невозможен. Не хватает средств на карте id = {}", fromCard.getId());
            throw new ConflictException("Трансфер невозможен. Не хватает средств на карте.");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(newTransferDto.getAmount()));
        toCard.setBalance(fromCard.getBalance().add(newTransferDto.getAmount()));
        cardRepository.saveAll(List.of(toCard, fromCard));

        Transfer transfer = Transfer.builder()
                .fromCard(fromCard)
                .toCard(toCard)
                .amount(newTransferDto.getAmount())
                .build();
        TransferDto transferDto = transferMapper.convertToDto(transferRepository.save(transfer));
        log.info("Внутренний трансфер средств произведен - {}", transferDto);
        return transferDto;
    }

    private void throwIfUserIsNotCardOwner(Card card, long userId) {
        if (!card.getOwner().getId().equals(userId)) {
            log.info("Трансфер невозможен. Пользователь id = {} не является владельцем карты id = {}.",
                    userId, card.getOwner().getId());
            throw new ConflictException("Пользователь не является владельцем карты");
        }
    }

    private void throwIfCardIsNotActive(Card card) {
        if (card.getStatus() != CardStatus.ACTIVE) {
            log.info("Конфликт статуса карты. Карта id = {} не активна.", card.getId());
            throw new ConflictException("Конфликт статуса карты. Карта id = " + card.getId()
                    + " не активна.");
        }
    }

    private Card getCardByIdOrElseThrow(long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> {
            log.info("Карты с cardId = {} не существует", cardId);
            return new NotFoundException("Карты id = " + cardId + " не существует");
        });
    }

    private void checkUserExists(long userId) {
        if (!userRepository.existsById(userId)) {
            log.info("Пользователя id = {} не существует", userId);
            throw new NotFoundException("Пользователя id = " + userId + " не существует");
        }
    }
}
