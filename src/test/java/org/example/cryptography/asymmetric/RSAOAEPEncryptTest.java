package org.example.cryptography.asymmetric;

import org.example.cryptography.Utils.CryptoUtils;
import org.example.cryptography.Utils.EncodeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.example.cryptography.Utils.CryptoUtils.RSA;
import static org.example.cryptography.Utils.EncodeUtils.CHUNK_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RSAOAEPEncryptTest {

    // --8<-- [start:crypto-values]

    /**
     * Optimal Asymmetric Encryption Padding (OAEP) allows for a message to be encrypted using RSA. It thus uses RSA
     * encryption and integrates a padding scheme.
     * OAP adds an element of randomness which can be used to convert a deterministic encryption scheme (e.g., traditional RSA)
     * into a probabilistic scheme.
     * OAP prevents partial decryption of ciphertexts (or other information leakage) by ensuring that an adversary cannot recover
     * any portion of the plaintext without being able to invert the trapdoor one-way permutation.
     */
    public static final String ENCRYPT_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    /**
     * Default Provider (It's not needed unless you change the default)
     */
    private static final String SUN_JCE = "SunJCE";

    /**
     * A mask generation function (MGF) is a cryptographic primitive similar to a cryptographic hash function
     * except that while a hash function's output has a fixed size, a MGF supports output of a variable length.
     * In this respect, a MGF can be viewed as a extendable-output function (XOF): it can accept input of any
     * length and process it to produce output of any length. Mask generation functions are completely deterministic:
     * for any given input and any desired output length the output is always the same.
     */
    private static final String MGF_1 = "MGF1";

    private static final String SHA_256 = "SHA-256";

    // --8<-- [end:crypto-values]

    @Test
    @DisplayName("Encrypt Decrypt with Symmetric key and RSA/None/OAEPWithSHA-256AndMGF1Padding Algorithm")
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

    // --8<-- [start:encrypt]

    private static byte[] encrypt(final byte[] data, final byte[] publicKey) throws GeneralSecurityException {
        final var keyFactory = KeyFactory.getInstance(RSA);
        final var publicKeySpec = new X509EncodedKeySpec(publicKey);
        final var publicKeyCipher = keyFactory.generatePublic(publicKeySpec);

        // Specify the provider to use, since it's not necessary using default provider
        final var cipher = Cipher.getInstance(ENCRYPT_ALGORITHM, SUN_JCE);
        // It's needed to specify OAEPParameterSpec, since by default it uses SHA-1 instead SHA-256 even it's specified
        final var spec = new OAEPParameterSpec(SHA_256, MGF_1, MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        cipher.init(Cipher.ENCRYPT_MODE, publicKeyCipher, spec);

        return cipher.doFinal(data);
    }

    // --8<-- [end:encrypt]

    // --8<-- [start:decrypt]

    private static byte[] decrypt(final byte[] data, final byte[] privateKey) throws GeneralSecurityException {
        final var keyFactory = KeyFactory.getInstance(RSA);
        final var privateKeySpec = new PKCS8EncodedKeySpec(privateKey);
        final var privateKeyCipher = keyFactory.generatePrivate(privateKeySpec);

        // It's needed to specify OAEPParameterSpec, since by default it uses SHA-1 instead SHA-256 even it's specified
        final var cipher = Cipher.getInstance(ENCRYPT_ALGORITHM, SUN_JCE);
        final var spec = new OAEPParameterSpec(SHA_256, MGF_1, MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
        cipher.init(Cipher.DECRYPT_MODE, privateKeyCipher, spec);

        return cipher.doFinal(data);
    }

    // --8<-- [end:decrypt]
}
