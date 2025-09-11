package com.example.bankcards.controller.user;

import com.example.bankcards.dto.card.CardBalanceDto;
import com.example.bankcards.dto.card.CardShortDto;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users/{userId}/cards")
@RequiredArgsConstructor
@Validated
@Tag(name = "Private: карты", description = "Private API для работы с картами")
public class CardPrivateController {
    private final CardService cardService;

    @Operation(summary = "Получение информации о картах пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выдан список карт пользователя"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping
    public Collection<CardShortDto> getAllCards(@PathVariable long userId,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return cardService.getAllCards(userId, from, size);
    }

    @Operation(summary = "Получение информации о балансе карты пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Информация о балансе карты получена"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена"),
            @ApiResponse(responseCode = "409", description = "Карта не соответствует правилам активности")
    })
    @GetMapping("/{cardId}")
    public CardBalanceDto getCardBalance(@PathVariable long userId, @PathVariable long cardId) {
        return cardService.getCardBalance(userId, cardId);
    }

    @Operation(summary = "Запрос блокировки карты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изменение статуса карты на заблокированную запрошено"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена"),
            @ApiResponse(responseCode = "409", description = "Карта не соответствует правилам запроса блокировки")
    })
    @PatchMapping("/{cardId}/request-block")
    public void requestBlockCard(@PathVariable long userId,
                                 @PathVariable long cardId) {
        cardService.requestBlockCard(userId, cardId);
    }
}
