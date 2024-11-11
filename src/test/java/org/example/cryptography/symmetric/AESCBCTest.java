package org.example.cryptography.symmetric;

import org.example.cryptography.Utils.CryptoUtils;
import org.example.cryptography.Utils.EncodeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;

import static org.example.cryptography.Utils.EncodeUtils.CHUNK_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AESCBCTest {

    // --8<-- [start:crypto-values]

    /**
     * AES + Cipher Block Chaining (CBC) + NoPadding
     * CBC uses the result from the previous encrypted block (XOR) to encrypt the next block (chain).
     * The first encrypted block uses an initialization vector (iv) for the XOR operations with random data.
     * CBC improves the ECB mode for minimizing patterns in plaintext.
     */
    public static final String ENCRYPT_ALGORITHM = "AES/CBC/NoPadding";

    // --8<-- [end:crypto-values]

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and AES/CBC/NoPadding Algorithm")
    public void encryptDecryptWithAES() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var symmetricKey = CryptoUtils.generateSymmetricKey();

        // Generate initialization vector (iv). It must be 16 bytes long
        final var iv = CryptoUtils.getRandomNonce();

        // The data to be encrypted and decrypted.
        // CBC Input length must be multiple of 16 bytes since it's not padding. i.e. 16 * 3 = 48bytes
        final var plainText = "This is an example that uses AES/CBC to encrypt.";
        System.out.printf("Plain Text: %s\n", plainText);

        // Encrypt the data using AES, symmetric key and initialization vector.
        final var encryptedData = encrypt(plainText.getBytes(), symmetricKey, iv);
        System.out.printf("Encrypted Text (blocks):\n%s\n", EncodeUtils.toHexSplit(encryptedData, CHUNK_SIZE));

        // Decrypt the data using AES, using the same symmetric key and initialization vector.
        final var decryptedData = decrypt(encryptedData, symmetricKey, iv);
        final var decryptedText = EncodeUtils.toString(decryptedData);

        System.out.printf("Decrypted '%s' must be equal to '%s'", decryptedText, plainText);

        assertEquals(plainText, decryptedText);
    }

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and AES/CCB/NoPadding Algorithm with input multiple of 16 bytes")
    public void encryptDecryptWithAESWithInputNotValidInLength() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var symmetricKey = CryptoUtils.generateSymmetricKey();

        // Generate initialization vector (iv). It must be 16 bytes long
        final var iv = CryptoUtils.getRandomNonce();

        // The data to be encrypted and decrypted.
        // ECB Input length must be multiple of 16 bytes since it's not padding. i.e. 16 * 3 = 48bytes
        final var plainText = "This is an example that uses AES/ECB to encrypt not multiple of 16 bytes.";
        System.out.printf("Plain Text: %s\n", plainText);

        // Encrypt the data using AES, symmetric key and initialization vector.
        assertThrows(IllegalBlockSizeException.class, () -> encrypt(plainText.getBytes(), symmetricKey, iv));

        System.out.println("IllegalBlockSizeException: Input length not multiple of 16 bytes");
    }

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and AES/CBC/NoPadding Algorithm With Pattern")
    public void encryptDecryptWithAESWithPattern() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var symmetricKey = CryptoUtils.generateSymmetricKey();

        // Generate initialization vector (iv)
        final var iv = CryptoUtils.getRandomNonce();

        // The data to be encrypted and decrypted.
        // ECB Input length must be multiple of 16 bytes since it's not padding. i.e. 16 * 3 = 48bytes
        final var plainText = "Hello world!!!!!Hello world!!!!!Hello world!!!!!";
        System.out.printf("Plain Text: %s\n", plainText);

        // Encrypt the data using AES, symmetric key and initialization vector.
        final var encryptedData = encrypt(plainText.getBytes(), symmetricKey, iv);
        System.out.printf("Encrypted Text (blocks):\n%s\n", EncodeUtils.toHexSplit(encryptedData, CHUNK_SIZE));

        // Decrypt the data using AES, using the same symmetric key and initialization vector.
        final var decryptedData = decrypt(encryptedData, symmetricKey, iv);
        final var decryptedText = EncodeUtils.toString(decryptedData);

        System.out.printf("Decrypted '%s' must be equal to '%s'", decryptedText, plainText);

        assertEquals(plainText, decryptedText);
    }

    // --8<-- [start:encrypt]

    private byte[] encrypt(final byte[] data, final SecretKey secretkey, final byte[] iv)
        throws GeneralSecurityException {
        final var encryptionCipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        final var ivParameterSpec = new IvParameterSpec(iv);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretkey, ivParameterSpec);
        return encryptionCipher.doFinal(data);
    }

    // --8<-- [end:encrypt]

    // --8<-- [start:decrypt]

    private byte[] decrypt(final byte[] data, final SecretKey secretkey, final byte[] iv)
        throws GeneralSecurityException {
        final var decryptionCipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        final var ivParameterSpec = new IvParameterSpec(iv);
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretkey, ivParameterSpec);
        return decryptionCipher.doFinal(data);
    }

    // --8<-- [end:decrypt]

}
