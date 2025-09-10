package com.example.bankcards.service;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.NewCardDto;

import java.util.Collection;

public interface CardService {
    public Collection<CardDto> getAllCards(int from, int size);

    public Collection<CardDto> getAllCards(long userId, int from, int size);

    public CardDto addNewCard(NewCardDto newCardDto);

    public CardDto blockCard(long cardId);

    public CardDto activateCard(long cardId);

    public void deleteCard(long cardId);
}
