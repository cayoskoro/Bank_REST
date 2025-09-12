package com.example.bankcards.mapper;

import com.example.bankcards.dto.card.CardBalanceDto;
import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.CardShortDto;
import com.example.bankcards.dto.card.NewCardDto;
import com.example.bankcards.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.control.DeepClone;

import java.time.YearMonth;
import java.util.Collection;

@Mapper(componentModel = "spring", mappingControl = DeepClone.class)
public interface CardMapper {
    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "panMask", source = "lastFourDigits", qualifiedByName = "panMask")
    public CardDto convertToDto(Card entity);

    @Mapping(target = "panMask", source = "lastFourDigits", qualifiedByName = "panMask")
    public CardShortDto convertToShortDto(Card entity);

    public CardBalanceDto convertToBalanceDto(Card entity);

    @Mapping(target = "cryptoNumber", source = "number")
    @Mapping(target = "lastFourDigits", source = "number", qualifiedByName = "substringLastFourDigits")
    @Mapping(target = "status", constant = "ACTIVATE")
    @Mapping(target = "blockRequest", constant = "false")
    @Mapping(target = "expiresAt", source = "expiresAt", defaultExpression = "java(YearMonth.now().plusMonths(24))")
    public Card convertNewCardDtoToEntity(NewCardDto dto);

    public Collection<CardDto> convertToDtoCollection(Collection<Card> entities);

    public Collection<CardShortDto> convertToShortDtoCollection(Collection<Card> entities);

    @Named("panMask")
    public static String panMask(String lastFourDigits) {
        if (lastFourDigits == null || lastFourDigits.length() < 4) {
            return "****";
        }
        return "**** **** **** " + lastFourDigits;
    }

    @Named("substringLastFourDigits")
    public static String substringLastFourDigits(String number) {
        if (number == null || number.length() < 4) {
            return null;
        }
        return number.substring(number.length() - 4);
    }
}
