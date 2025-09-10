package com.example.bankcards.mapper;

import com.example.bankcards.dto.card.CardRequestDto;
import com.example.bankcards.dto.card.CardResponseDto;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;

import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface CardMapper {
    public CardResponseDto convertToDto(Card entity);

    public Collection<CardResponseDto> convertToDtoCollection(Collection<Card> entities);
}
