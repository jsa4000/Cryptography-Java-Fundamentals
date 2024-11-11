package org.example.cryptography.Utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class CryptoUtils {

    // --8<-- [start:generate-symmetric-key-values]

    /**
     * The Advanced Encryption Standard (AES, Rijndael) is a block cipher encryption and decryption
     * algorithm, the most used encryption algorithm in the worldwide. The AES processes block of 128
     * bits using a secret key of 128, 192, or 256 bits.
     */
    public static final String AES = "AES";

    /**
     * For AES, the legal key sizes are 128, 192, and 256 bits.
     */
    public static final int AES_KEY_SIZE = 128;

    /**
     * The IV (initial value or initialization vector), it is random bytes, typically 12 bytes or 16 bytes
     */
    public static final int INITIALIZATION_VECTOR_SIZE = 16;

    // --8<-- [end:generate-symmetric-key-values]

    // --8<-- [start:generate-asymmetric-key-values]

    /**
     * In RSA cryptography, both the public and the private keys can encrypt a message. The opposite key
     * from the one used to encrypt a message is used to decrypt it. This attribute is one reason why RSA
     * has become the most widely used asymmetric algorithm: It provides a method to assure the confidentiality,
     * integrity, authenticity, and non-repudiation of electronic communications and data storage.
     * RSA requires more intensive processing than AES, because of that RSA is used to encrypt AES keys or
     * small data in transit.
     */
    public static final String RSA = "RSA";

    /**
     * For RSA the larger the key the more secure the encryption will be.
     */
    public static final int RSA_KEY_SIZE = 2048;

    // --8<-- [end:generate-asymmetric-key-values]

    // --8<-- [start:generate-symmetric-key]

    /**
     * @return
     * @throws GeneralSecurityException
     */
    public static SecretKey generateSymmetricKey() throws GeneralSecurityException {
        // Get the Key Generator
        final var keyGenerator = KeyGenerator.getInstance(AES);
        // Init the AES Symmetric key using 128 bits
        keyGenerator.init(AES_KEY_SIZE, SecureRandom.getInstanceStrong());
        return keyGenerator.generateKey();
    }

    // --8<-- [end:generate-symmetric-key]

    /**
     * @param symmetricKey
     * @return
     */
    public static SecretKey generateSymmetricKey(final byte[] symmetricKey) {
        return new SecretKeySpec(symmetricKey, AES);
    }

    // --8<-- [start:generate-asymmetric-key]

    /**
     * @return
     * @throws Exception
     */
    public static KeyPair generateAsymmetricKeyPair() throws NoSuchAlgorithmException {
        final var keyPairGenerator = KeyPairGenerator.getInstance(RSA); // (1)!
        keyPairGenerator.initialize(RSA_KEY_SIZE); // (2)!
        return keyPairGenerator.generateKeyPair();
    }

    // --8<-- [end:generate-asymmetric-key]

    // --8<-- [start:generate-symmetric-iv]

    /**
     * A cryptographic nonce is a randomly generated number designed to keep communications private and
     * protect against replay attacks.
     *
     * @return
     */
    public static byte[] getRandomNonce() {
        final var nonce = new byte[INITIALIZATION_VECTOR_SIZE];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    // --8<-- [end:generate-symmetric-iv]

}

