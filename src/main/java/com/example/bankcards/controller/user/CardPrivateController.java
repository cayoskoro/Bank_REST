package com.example.bankcards.controller.user;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users/{userId}/cards")
@Validated
@RequiredArgsConstructor
public class CardPrivateController {
    private final CardService cardService;

    @GetMapping
    public Collection<CardDto> getAllCards(@PathVariable long userId,
                                           @RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "0") int size) {
        return cardService.getAllCards(userId, from, size);
    }

    @GetMapping("/{cardId}")
    public CardDto getBalanceCard(@PathVariable long userId, @PathVariable long cardId) {
        return cardService.getBalanceCard(userId, cardId);
    }

    @PatchMapping("/{cardId}/request-block")
    public CardDto requestBlockCard(@PathVariable long userId,
                                    @PathVariable long cardId) {
        return cardService.addNewBlockRequest(userId, cardId);
    }
}
