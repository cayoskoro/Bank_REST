package com.example.bankcards.service.impl;

import com.example.bankcards.dto.transfer.NewTransferRequestDto;
import com.example.bankcards.dto.transfer.TransferResponseDto;
import com.example.bankcards.entity.*;
import com.example.bankcards.exception.CardExpiredException;
import com.example.bankcards.exception.CardStatusConflictException;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

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
    public TransferResponseDto transferInternalFunds(long userId, NewTransferRequestDto newTransferRequestDto) {
        User user = getUserByIdOrElseThrow(userId);

        Card fromCard = getCardByIdOrElseThrow(newTransferRequestDto.getFromCard());
        throwIfUserIsNotCardOwner(fromCard, user.getId());
        throwIfCardIsNotActive(fromCard);

        Card toCard = getCardByIdOrElseThrow(newTransferRequestDto.getToCard());
        throwIfUserIsNotCardOwner(toCard, user.getId());
        throwIfCardIsNotActive(toCard);

        if (fromCard.getBalance().compareTo(newTransferRequestDto.getAmount()) < 0) {
            log.info("Трансфер невозможен. Не хватает средств на карте id = {}", fromCard.getId());
            throw new ConflictException("Трансфер невозможен. Не хватает средств на карте id = " + fromCard.getId());
        }

        fromCard.setBalance(fromCard.getBalance().subtract(newTransferRequestDto.getAmount()));
        toCard.setBalance(fromCard.getBalance().add(newTransferRequestDto.getAmount()));


        Transfer transfer = Transfer.builder()
                .fromCard(fromCard)
                .toCard(toCard)
                .amount(newTransferRequestDto.getAmount())
                .status(TransferStatus.SUCCESS)
                .build();
        TransferResponseDto transferResponseDto = transferMapper.convertToDto(transferRepository.save(transfer));
        log.info("Внутренний трансфер средств произведен - {}", transferResponseDto);
        return transferResponseDto;
    }

/*    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void saveRejectedTransfer(NewTransferRequestDto newTransferRequestDto) {
        Transfer transfer = Transfer.builder()
                .fromCard()
                .toCard()
                .amount(newTransferRequestDto.getAmount())
                .status(TransferStatus.REJECTED)
                .build();
        transferRepository.save(transfer);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void saveErrorTransfer(NewTransferRequestDto newTransferRequestDto) {
        Transfer transfer = Transfer.builder()
                .fromCard()
                .toCard()
                .amount(newTransferRequestDto.getAmount())
                .status(TransferStatus.ERROR)
                .build();
        transferRepository.save(transfer);
    }*/

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
            throw new CardStatusConflictException("Конфликт статуса карты. Карта id = " + card.getId()
                    + " не активна.");
        }
    }

    private Card getCardByIdOrElseThrow(long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> {
            log.info("Карты с cardId = {} не существует", cardId);
            return new NotFoundException("Карты id = " + cardId + " не существует");
        });
    }

    private User getUserByIdOrElseThrow(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.info("Пользователя id = {} не существует", userId);
            return new NotFoundException("Пользователя id = " + userId + " не существует");
        });
    }
}
