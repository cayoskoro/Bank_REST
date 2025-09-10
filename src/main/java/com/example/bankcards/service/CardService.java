package com.example.bankcards.service;

import com.example.bankcards.dto.card.CardResponseDto;
import com.example.bankcards.dto.card.NewCardRequestDto;

import java.util.Collection;

public interface CardService {
    public Collection<CardResponseDto> getAllCards(int from, int size);

    public Collection<CardResponseDto> getAllCards(long userId, int from, int size);

    public CardResponseDto addNewCard(NewCardRequestDto newCardRequestDto);

    public CardResponseDto blockCard(long cardId);

    public CardResponseDto activateCard(long cardId);

    public void deleteCard(long cardId);
}
