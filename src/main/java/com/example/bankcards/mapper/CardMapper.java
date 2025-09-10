package com.example.bankcards.mapper;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.NewCardDto;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.control.DeepClone;

import java.time.YearMonth;
import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface CardMapper {
    public CardDto convertToDto(Card entity);

    @Mapping(target = "owner.id", source = "ownerId")
    @Mapping(target = "status", constant = "ACTIVATE")
    @Mapping(target = "balance", defaultValue = "0.0")
    @Mapping(target = "expiresAt", source = "expiresAt", defaultExpression = "java(YearMonth.now().plusMonths(24))")
    Card convertNewCardDtoToEntity(NewCardDto dto);

    public Collection<CardDto> convertToDtoCollection(Collection<Card> entities);
}
