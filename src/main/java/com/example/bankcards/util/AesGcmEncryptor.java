package com.example.bankcards.util;

import com.example.bankcards.config.EncryptorProperties;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public final class AesGcmEncryptor implements Encryptor {
    private static final String SHA_CRYPT = "SHA-256";
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_ALGORITHM_GCM = "AES/GCM/NoPadding";
    private final EncryptorProperties encryptorProperties;

    public AesGcmEncryptor(EncryptorProperties encryptorProperties) {
        this.encryptorProperties = encryptorProperties;
    }

    public String encrypt(String input) throws Exception {
        if (input == null) return null;
        byte[] iv = new byte[encryptorProperties.getIvLength()];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);

        SecretKeySpec aesKey = generateAesKeyFromPassphrase();

        Cipher cipher = Cipher.getInstance(AES_ALGORITHM_GCM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(encryptorProperties.getTagLength() * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);

        byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

        byte[] combinedIvAndCipher = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combinedIvAndCipher, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combinedIvAndCipher, iv.length, encryptedBytes.length);

        return Base64.getEncoder().encodeToString(combinedIvAndCipher);
    }

    public String decrypt(String encryptedInput) throws Exception {
        if (encryptedInput == null) return null;
        byte[] decodedEncryptedInput = Base64.getDecoder().decode(encryptedInput);

        SecretKeySpec aesKey = generateAesKeyFromPassphrase();

        byte[] iv = new byte[encryptorProperties.getIvLength()];
        System.arraycopy(decodedEncryptedInput, 0, iv, 0, iv.length);
        byte[] encryptedBytes = new byte[decodedEncryptedInput.length - encryptorProperties.getIvLength()];
        System.arraycopy(decodedEncryptedInput, encryptorProperties.getIvLength(), encryptedBytes, 0,
                encryptedBytes.length);

        GCMParameterSpec gcmSpec = new GCMParameterSpec(encryptorProperties.getTagLength() * 8, iv);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM_GCM);
        cipher.init(Cipher.DECRYPT_MODE, aesKey, gcmSpec);

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private SecretKeySpec generateAesKeyFromPassphrase() throws Exception {
        MessageDigest sha256 = MessageDigest.getInstance(SHA_CRYPT);
        byte[] keyBytes = sha256.digest(encryptorProperties.getPassphrase().getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, AES_ALGORITHM);
    }
}
