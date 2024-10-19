package org.example.cryptography.Utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class CryptoUtils {

    /**
     * The Advanced Encryption Standard (AES, Rijndael) is a block cipher encryption and decryption
     * algorithm, the most used encryption algorithm in the worldwide. The AES processes block of 128
     * bits using a secret key of 128, 192, or 256 bits.
     */
    public static final String KEY_ALGORITHM = "AES";

    /**
     * For AES, the legal key sizes are 128, 192, and 256 bits.
     */
    public static final int KEY_SIZE = 128;

    /**
     * The IV (initial value or initialization vector), it is random bytes, typically 12 bytes or 16 bytes
     */
    public static final int INITIALIZATION_VECTOR_SIZE = 16;

    /**
     * @return
     * @throws GeneralSecurityException
     */
    public static SecretKey generateSymmetricKey() throws GeneralSecurityException {
        // Get the Key Generator
        final var keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
        // Init the AES Symmetric key using 128 bits
        keyGenerator.init(KEY_SIZE, SecureRandom.getInstanceStrong());
        return keyGenerator.generateKey();
    }

    /**
     * @param symmetricKey
     * @return
     */
    public static SecretKey generateSymmetricKey(final byte[] symmetricKey) {
        return new SecretKeySpec(symmetricKey, KEY_ALGORITHM);
    }

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

}

