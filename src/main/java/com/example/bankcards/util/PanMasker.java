package com.example.bankcards.util;

public final class PanMasker {

    private PanMasker() {
    }

    public static String mask(String lastFourNumbers) {
        return "**** **** **** " + lastFourNumbers;
    }
}
