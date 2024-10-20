package org.example.cryptography.symmetric;

import org.example.cryptography.Utils.CryptoUtils;
import org.example.cryptography.Utils.EncodeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.GeneralSecurityException;

import static org.example.cryptography.Utils.EncodeUtils.CHUNK_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AESGCMTest {

    /**
     * AES + Galois Counter Mode (GCM) + NoPadding
     * GCM = CTR + Authentication
     * GCM Concatenates an initialization vector with a counter in order to add more randomness to the
     * generated encrypted data. This is to avoid repetition and add more noise into the blocks.
     * It also adds authentication to the algorithm by the generation of a Tag.
     * Don’t use AES Electronic codebook (ECB) Mode. The AES ECB mode, or AES/ECB/PKCS5Padding (in Java)
     * is not semantically secure – The ECB-encrypted ciphertext can leak information about the plaintext.
     * GCM since it's more secured it takes more time to be computed than other algorithm such as CBC or ECB.
     */
    public static final String ENCRYPT_ALGORITHM = "AES/GCM/NoPadding"; // AES/GCM/PKCS5Padding

    /**
     * GCM is defined for the tag sizes 128, 120, 112, 104, or 96, 64 and 32.
     * Note that the security of GCM is strongly dependent on the tag size.
     * You should try and use a tag size of 64 bits at the very minimum, but in general a tag
     * size of the full 128 bits should be preferred.
     */
    public static final int AUTHENTICATION_TAG_SIZE = 128;

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and AES/GCM/NoPadding Algorithm")
    public void encryptDecryptWithAES() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var symmetricKey = CryptoUtils.generateSymmetricKey();

        // Generate initialization vector (iv)
        final var iv = CryptoUtils.getRandomNonce();

        // The data to be encrypted and decrypted
        // With GCM Input length not need to be multiple of 16 bytes
        final var plainText = "This is an example that uses AES/GCM to encrypt information.";
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
    @DisplayName("Encrypt Decrypt with Symmetric key and AES/GCM/NoPadding Algorithm With Pattern")
    public void encryptDecryptWithAESWithPattern() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var symmetricKey = CryptoUtils.generateSymmetricKey();

        // Generate initialization vector (iv)
        final var iv = CryptoUtils.getRandomNonce();

        // The data to be encrypted and decrypted
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

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and AES/GCM/NoPadding Algorithm from exiting key")
    public void encryptDecryptWithAESExported() throws GeneralSecurityException {

        // Generate the Symmetric key and initialization vector and encode them to base64
        final var initialSymmetricKey = EncodeUtils.encode(CryptoUtils.generateSymmetricKey().getEncoded());
        final var initialIv = EncodeUtils.encode(CryptoUtils.getRandomNonce());

        // Decode  initialization vector (iv)
        final var symmetricKey = CryptoUtils.generateSymmetricKey(initialSymmetricKey.getBytes());
        final var iv = EncodeUtils.decode(initialIv.getBytes()).getBytes();

        // The data to be encrypted and decrypted
        final var plainText = "This is an example that uses AES/GCM to encrypt information.";
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

    private byte[] encrypt(final byte[] data, final SecretKey secretkey, final byte[] iv)
        throws GeneralSecurityException {
        final var encryptionCipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretkey, new GCMParameterSpec(AUTHENTICATION_TAG_SIZE, iv));
        return encryptionCipher.doFinal(data);
    }

    private byte[] decrypt(final byte[] data, final SecretKey secretkey, final byte[] iv)
        throws GeneralSecurityException {
        final var decryptionCipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretkey, new GCMParameterSpec(AUTHENTICATION_TAG_SIZE, iv));
        return decryptionCipher.doFinal(data);
    }

}
