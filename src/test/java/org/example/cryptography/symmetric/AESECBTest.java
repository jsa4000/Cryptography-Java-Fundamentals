package org.example.cryptography.symmetric;

import org.example.cryptography.Utils.CryptoUtils;
import org.example.cryptography.Utils.EncodeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;

import static org.example.cryptography.Utils.EncodeUtils.CHUNK_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AESECBTest {

    /**
     * AES + Electronic Code Book (ECB) + NoPadding
     * ECB is the easiest block cipher way of functioning, since each block of input plaintext is directly encrypted,
     * and the output is in the form of encrypted ciphertext blocks. In general, if a message is bigger than b bits
     * in size, it can be divided into many blocks and the process repeated.
     */
    public static final String ENCRYPT_ALGORITHM = "AES/ECB/NoPadding";

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and AES/ECB/NoPadding Algorithm")
    public void encryptDecryptWithAES() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var symmetricKey = CryptoUtils.generateSymmetricKey();

        // The data to be encrypted and decrypted.
        // ECB Input length must be multiple of 16 bytes since it's not padding. i.e. 16 * 3 = 48bytes
        final var plainText = "This is an example that uses AES/ECB to encrypt.";
        System.out.printf("Plain Text: %s\n", plainText);

        // Encrypt the data using AES, symmetric key and initialization vector.
        final var encryptedData = encrypt(plainText.getBytes(), symmetricKey);
        System.out.printf("Encrypted Text (blocks):\n%s\n", EncodeUtils.toHexSplit(encryptedData, CHUNK_SIZE));

        // Decrypt the data using AES, using the same symmetric key and initialization vector.
        final var decryptedData = decrypt(encryptedData, symmetricKey);
        final var decryptedText = EncodeUtils.toString(decryptedData);

        System.out.printf("Decrypted '%s' must be equal to '%s'", decryptedText, plainText);

        assertEquals(plainText, decryptedText);
    }

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and AES/ECB/NoPadding Algorithm with input multiple of 16 bytes")
    public void encryptDecryptWithAESWithInputNotValidInLength() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var symmetricKey = CryptoUtils.generateSymmetricKey();

        // The data to be encrypted and decrypted.
        // ECB Input length must be multiple of 16 bytes since it's not padding. i.e. 16 * 3 = 48bytes
        final var plainText = "This is an example that uses AES/ECB to encrypt not multiple of 16 bytes.";
        System.out.printf("Plain Text: %s\n", plainText);

        // Encrypt the data using AES, symmetric key and initialization vector.
        assertThrows(IllegalBlockSizeException.class, () -> encrypt(plainText.getBytes(), symmetricKey));

        System.out.println("IllegalBlockSizeException: Input length not multiple of 16 bytes");
    }

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and AES/ECB/NoPadding Algorithm With Pattern")
    public void encryptDecryptWithAESWithPattern() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var symmetricKey = CryptoUtils.generateSymmetricKey();

        // The data to be encrypted and decrypted.
        // ECB Input length must be multiple of 16 bytes since it's not padding. i.e. 16 * 3 = 48bytes
        final var plainText = "Hello world!!!!!Hello world!!!!!Hello world!!!!!";
        System.out.printf("Plain Text: %s\n", plainText);

        // Encrypt the data using AES, symmetric key and initialization vector.
        final var encryptedData = encrypt(plainText.getBytes(), symmetricKey);
        System.out.printf("Encrypted Text (blocks):\n%s\n", EncodeUtils.toHexSplit(encryptedData, CHUNK_SIZE));

        // Decrypt the data using AES, using the same symmetric key and initialization vector.
        final var decryptedData = decrypt(encryptedData, symmetricKey);
        final var decryptedText = EncodeUtils.toString(decryptedData);

        System.out.printf("Decrypted '%s' must be equal to '%s'", decryptedText, plainText);

        assertEquals(plainText, decryptedText);
    }

    private byte[] encrypt(final byte[] data, final SecretKey secretkey)
        throws GeneralSecurityException {
        final var encryptionCipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretkey);
        return encryptionCipher.doFinal(data);
    }

    private byte[] decrypt(final byte[] data, final SecretKey secretkey)
        throws GeneralSecurityException {
        final var decryptionCipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretkey);
        return decryptionCipher.doFinal(data);
    }

}
