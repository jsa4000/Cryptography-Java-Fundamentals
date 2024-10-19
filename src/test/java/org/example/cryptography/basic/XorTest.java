package org.example.cryptography.basic;

import org.example.cryptography.Utils.EncodeUtils;
import org.example.cryptography.Utils.XorUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class XorTest {

    @Test
    @DisplayName("Encrypt binary ASCII character using XOR logic operation.")
    public void encryptBinaryCharWithXOR() {
        final var keySize = 8;

        // Generate a Random Secret Key with 8 bits
        // Warning: is not a secure method but is fundamental to understand encryption
        final var secretKey = XorUtils.getRandomNumberInRange(0, 255);
        System.out.printf("SecretKey is: %s\n", EncodeUtils.toBinaryStringWithPadding(secretKey, keySize));

        // Generate a plain Data to be encrypted (from 'a' to 'z')
        final var plainData = XorUtils.getRandomNumberInRange(97, 122);
        System.out.printf("PlainData is: %s -> %s\n",
            EncodeUtils.toStringByteFromInt(plainData),
            EncodeUtils.toBinaryStringWithPadding(plainData, keySize));

        // Encrypt the data using a simple XOR operation
        final var encryptedText = plainData ^ secretKey;

        System.out.printf("Applied XOR Encryption to: %s\n",
            EncodeUtils.toStringByteFromInt(plainData));
        System.out.println(EncodeUtils.toBinaryStringWithPadding(plainData, keySize));
        System.out.println(EncodeUtils.toBinaryStringWithPadding(secretKey, keySize));
        System.out.println("-------");
        System.out.println(EncodeUtils.toBinaryStringWithPadding(encryptedText, keySize));
    }

    @Test
    @DisplayName("Decrypt binary ASCII character using XOR logic operation.")
    public void decryptBinaryCharWithXOR() {
        final var keySize = 8;

        // Generate a Random Secret Key with 8 bits (not secure)
        // Warning: is not a secure method but is fundamental to understand encryption
        final var secretKey = XorUtils.getRandomNumberInRange(0, 255);
        System.out.printf("SecretKey is: %s\n", EncodeUtils.toBinaryStringWithPadding(secretKey, keySize));

        // Generate a plain Data to be encrypted (from 'a' to 'z')
        final var plainData = XorUtils.getRandomNumberInRange(97, 122);
        System.out.printf("PlainData is: %s -> %s\n",
            EncodeUtils.toStringByteFromInt(plainData),
            EncodeUtils.toBinaryStringWithPadding(plainData, keySize));

        // Encrypt the data using a simple XOR operation
        final var encryptedText = plainData ^ secretKey;

        System.out.printf("Applied XOR Encryption to: %s\n",
            EncodeUtils.toStringByteFromInt(plainData));
        System.out.println(EncodeUtils.toBinaryStringWithPadding(plainData, keySize));
        System.out.println(EncodeUtils.toBinaryStringWithPadding(secretKey, keySize));
        System.out.println("-------");
        System.out.println(EncodeUtils.toBinaryStringWithPadding(encryptedText, keySize));

        // Decrypt the data using a simple XOR operation
        final var decryptedText = encryptedText ^ secretKey;

        System.out.println();
        System.out.printf("Applied XOR Decryption to: %s -> %s\n",
            EncodeUtils.toStringByteFromInt(decryptedText),
            EncodeUtils.toStringByteFromInt(plainData));
        System.out.println(EncodeUtils.toBinaryStringWithPadding(encryptedText, keySize));
        System.out.println(EncodeUtils.toBinaryStringWithPadding(secretKey, keySize));
        System.out.println("-------");
        System.out.println(EncodeUtils.toBinaryStringWithPadding(decryptedText, keySize));

        assertEquals(plainData, decryptedText);
    }

    @Test
    @DisplayName("Decrypt characters using XOR logic operation.")
    public void decryptWithXOR() {
        final var plainText = "My Text Example."; // 16 chars/bytes
        final var keySize = plainText.length() * 8;

        // Generate a Random Secret Key with n bits (key and data must be equal in size)
        final var secretKey = XorUtils.generateKey(keySize);

        // Encrypt the data using a simple XOR operation
        final var encryptedText = XorUtils.xor(plainText.getBytes(), secretKey);

        // Decrypt the data using a simple XOR operation
        final var decryptedText = new String(XorUtils.xor(encryptedText, secretKey),
            StandardCharsets.UTF_8);

        System.out.printf("%s is equal to %s\n", plainText, decryptedText);

        assertEquals(plainText, decryptedText);
    }

    @Test
    @DisplayName("Decrypt characters using XOR and different Secret Keys.")
    public void decryptWithXORAndDifferentSecretKey() {
        final var plainText = "My Text Example."; // 16 chars/bytes
        final var keySize = plainText.length() * 8;

        // Generate a Random Secret Key with n bits (key and data must be equal in size)
        final var secretKey = XorUtils.generateKey(keySize);

        // Encrypt the data using a simple XOR operation
        final var encryptedText = XorUtils.xor(plainText.getBytes(), secretKey);

        // Generate new secret key and decrypt the data using a simple XOR operation
        final var newSecretKey = XorUtils.generateKey(keySize);
        final var decryptedText = new String(XorUtils.xor(encryptedText, newSecretKey),
            StandardCharsets.UTF_8);

        System.out.printf("%s is NOT equal to %s\n", plainText, decryptedText);

        assertNotEquals(plainText, decryptedText);
    }

}
