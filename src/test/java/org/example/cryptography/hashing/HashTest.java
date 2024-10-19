package org.example.cryptography.hashing;

import org.example.cryptography.Utils.CryptoUtils;
import org.example.cryptography.Utils.EncodeUtils;
import org.example.cryptography.Utils.HashUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HashTest {

    /**
     * Hash algorithm. It's recommended to use SHA-256 over legacy (non-trusted) algorithms such as SHA-1 or MD5
     */
    public static final String DEFAULT_HASH_ALGORITHM = "SHA-256"; // ["SHA-256", "SHA-1", "MD5"]

    @Test
    @DisplayName("Generated Hash must preserve hashing properties")
    public void hashWithSHA() throws NoSuchAlgorithmException {

        System.out.println("One-way only");
        final var plainText = "This is a sample test to be hashing.";
        final var textHash = generateHashData(plainText.getBytes());
        System.out.println(textHash);

        System.out.println("Deterministic");
        final var deterministicText = "This is a sample test to be hashing.";
        final var deterministicHash = generateHashData(deterministicText.getBytes());
        System.out.println(deterministicHash);

        System.out.println("Pseudo Random");
        final var pseudoRandomText = "This is a sample test to be hashing;"; // End is different
        final var pseudoRandomHash = generateHashData(pseudoRandomText.getBytes());
        System.out.println(pseudoRandomHash);

        System.out.println("Fixed Length");
        final var fixedLengthTextTest = "This is a sample test to be hashing with more characters";
        final var fixedLengthTextHash = generateHashData(fixedLengthTextTest.getBytes());
        System.out.println(fixedLengthTextHash);

        assertEquals(textHash.base64(), deterministicHash.base64());
        assertNotEquals(textHash.base64(), pseudoRandomHash.base64());
        assertEquals(textHash.base64().length(), fixedLengthTextHash.base64().length());
    }

    @Test
    @DisplayName("Generated Hash with Salt must provide different values (Simple Password Hashing)")
    public void hashWithSalt() throws NoSuchAlgorithmException {

        final var plainText = "This is a sample test to be hashing.";

        System.out.println("Generate Hash without Salt");
        final var textHashNoSalt1 = generateHashData(plainText.getBytes());
        System.out.println(textHashNoSalt1);

        System.out.println("Generate different Hash without Salt (must be equal)");
        final var textHashNoSalt2 = generateHashData(plainText.getBytes());
        System.out.println(textHashNoSalt2);

        System.out.println("Generate Hash with Salt");
        final var salt1 = CryptoUtils.getRandomNonce();
        final var textHash1 = generateHashDataWithSalt(plainText.getBytes(), salt1);
        System.out.println(textHash1);

        System.out.println("Generate Hash with different Salt (must be different)");
        final var salt2 = CryptoUtils.getRandomNonce();
        final var textHash2 = generateHashDataWithSalt(plainText.getBytes(), salt2);
        System.out.println(textHash2);

        assertEquals(textHashNoSalt1.base64(), textHashNoSalt2.base64());
        assertNotEquals(textHash1.base64(), textHash2.base64());
    }

    private HashUtils.HashData generateHashData(final byte[] data) throws NoSuchAlgorithmException {
        final var hash = hash(data);
        return new HashUtils.HashData(data, hash, EncodeUtils.encode(hash));
    }

    private HashUtils.HashData generateHashDataWithSalt(final byte[] data, final byte[] salt) throws NoSuchAlgorithmException {
        final var hash = hashWithSalt(data, salt);
        return new HashUtils.HashData(data, hash, EncodeUtils.encode(hash));
    }

    private byte[] hash(final byte[] data) throws NoSuchAlgorithmException {
        // Create Hash function to generate the digest using the specified algorithm.
        final var messageDigest = MessageDigest.getInstance(DEFAULT_HASH_ALGORITHM);
        return messageDigest.digest(data);
    }

    private byte[] hashWithSalt(final byte[] data, final byte[] salt) throws NoSuchAlgorithmException {
        // Create Hash function to generate the digest using the specified algorithm.
        final var messageDigest = MessageDigest.getInstance(DEFAULT_HASH_ALGORITHM);
        messageDigest.update(salt);
        return messageDigest.digest(data);
    }

}
