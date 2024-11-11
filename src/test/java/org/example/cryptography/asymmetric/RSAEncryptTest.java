package org.example.cryptography.asymmetric;

import org.example.cryptography.Utils.CryptoUtils;
import org.example.cryptography.Utils.EncodeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.example.cryptography.Utils.CryptoUtils.RSA;
import static org.example.cryptography.Utils.EncodeUtils.CHUNK_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RSAEncryptTest {

    // --8<-- [start:rsa-encrypt-values]

    /**
     * The Java algorithm string "RSA/ECB/PKCS1Padding", as you already found out, does not implement ECB;
     * it only encrypts/decrypts a single block. The Bouncy Castle cryptographic security provider has a better
     * named algorithm string, "RSA/None/PKCS1Padding", which better indicates that no mode of operation is used.
     * It is likely that "/ECB" was just included to mimic the cipher string for block ciphers. So you would have
     * to call the cipher "RSA/ECB/PKCS1Padding" multiple times to implement ECB.
     * "PKCS1Padding" indicates RSA with PKCS#1 v1.5 padding for encryption. This padding is indeterministic -
     * i.e. it uses a random number generator. This explains why each ciphertext block will be different.
     */
    public static final String ENCRYPT_ALGORITHM = "RSA/ECB/PKCS1Padding";

    // --8<-- [end:rsa-encrypt-values]

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and RSA/None/PKCS1Padding Algorithm")
    public void encryptDecryptWithAES() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var keyPair = CryptoUtils.generateAsymmetricKeyPair();

        // The data to be encrypted and decrypted.
        final var plainText = "This is an example that uses RSA to encrypt.";
        System.out.printf("Plain Text: %s\n", plainText);

        // Encrypt the data using RSA with public key
        final var encryptedData = encrypt(plainText.getBytes(), keyPair.getPublic().getEncoded());
        System.out.printf("Encrypted Text (blocks):\n%s\n", EncodeUtils.toHexSplit(encryptedData, CHUNK_SIZE));

        // Decrypt the data using RSA with private key
        final var decryptedData = decrypt(encryptedData, keyPair.getPrivate().getEncoded());
        final var decryptedText = EncodeUtils.toString(decryptedData);

        System.out.printf("Decrypted '%s' must be equal to '%s'", decryptedText, plainText);

        assertEquals(plainText, decryptedText);
    }

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and RSA Algorithm with data length greater than 245 bytes")
    public void encryptDecryptWithAESWithLargeData() throws Exception {
        // Generate the Symmetric key
        final var keyPair = CryptoUtils.generateAsymmetricKeyPair();

        // The data to be encrypted and decrypted (greater than 245 bytes).
        final var data = Files.readString(Paths.get(getClass().getClassLoader().getResource("files/large.txt").toURI()));
        System.out.printf("Plain Text: %s\n", data);

        // Check that it throws IllegalBlockSizeException: Data must not be longer than 245 bytes
        assertThrows(IllegalBlockSizeException.class, () -> encrypt(data.getBytes(), keyPair.getPublic().getEncoded()));

        System.out.println("IllegalBlockSizeException: Data must not be longer than 245 bytes");
    }

    // --8<-- [start:rsa-encrypt]

    private static byte[] encrypt(final byte[] data, final byte[] publicKey) throws GeneralSecurityException {
        final var keyFactory = KeyFactory.getInstance(RSA);
        final var publicKeySpec = new X509EncodedKeySpec(publicKey);
        final var publicKeyCipher = keyFactory.generatePublic(publicKeySpec);

        final var cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKeyCipher);

        return cipher.doFinal(data);
    }

    // --8<-- [end:rsa-encrypt]

    // --8<-- [start:rsa-decrypt]

    private static byte[] decrypt(final byte[] data, final byte[] privateKey) throws GeneralSecurityException {
        final var keyFactory = KeyFactory.getInstance(RSA);
        final var privateKeySpec = new PKCS8EncodedKeySpec(privateKey);
        final var privateKeyCipher = keyFactory.generatePrivate(privateKeySpec);

        final var cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKeyCipher);

        return cipher.doFinal(data);
    }

    // --8<-- [end:rsa-decrypt]
}
