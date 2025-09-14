package com.example.bankcards.controller.admin;


import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.card.NewCardDto;
import com.example.bankcards.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(path = "/admin/cards")
@RequiredArgsConstructor
@Validated
@Tag(name = "Admin: карты", description = "Admin API для работы с картами")
public class CardAdminController {
    private final CardService cardService;

    @Operation(summary = "Получение информации о картах")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выдан список карт")
    })
    @GetMapping
    public Collection<CardDto> getAllCards(@RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size) {
        return cardService.getAllCards(from, size);
    }

    @Operation(summary = "Добавление новой карты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Карта добавлена"),
            @ApiResponse(responseCode = "409", description = "Нарушение целостности данных")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto addNewCard(@RequestBody @Valid NewCardDto newCardDto) {
        return cardService.addNewCard(newCardDto);
    }

    @Operation(summary = "Блокировка карты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Карта заблокирована"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена"),
            @ApiResponse(responseCode = "409", description = "Карта не соответствует правилам блокирования")
    })
    @PatchMapping("/{cardId}/block")
    public CardDto blockCard(@PathVariable long cardId) {
        return cardService.blockCard(cardId);
    }

    @Operation(summary = "Активация карты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Карта активирована"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена"),
            @ApiResponse(responseCode = "409", description = "Карта не соответствует правилам активации")
    })
    @PatchMapping("/{cardId}/activate")
    public CardDto activateCard(@PathVariable long cardId) {
        return cardService.activateCard(cardId);
    }

    @Operation(summary = "Удаление карты")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Карта удалена"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена или недоступна")
    })
    @DeleteMapping("/{cardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCard(@PathVariable long cardId) {
        cardService.deleteCard(cardId);
    }
}
