package com.example.bankcards.service;

import com.example.bankcards.dto.card.CardBalanceDto;
import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.CardShortDto;
import com.example.bankcards.dto.card.NewCardDto;

import java.util.Collection;

public interface CardService {
    public Collection<CardDto> getAllCards(int from, int size);

    public Collection<CardShortDto> getAllCards(long userId, int from, int size);

    public CardBalanceDto getCardBalance(long userId, long cardId);

    public CardDto addNewCard(NewCardDto newCardDto);

    public void requestBlockCard(long userId, long cardId);

    public CardDto blockCard(long cardId);

    public CardDto activateCard(long cardId);

    public void deleteCard(long cardId);
}
