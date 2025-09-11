package com.example.bankcards.util;

public interface Encryptor {

    public String encrypt(String input) throws Exception;

    public String decrypt(String encryptedInput) throws Exception;
}
