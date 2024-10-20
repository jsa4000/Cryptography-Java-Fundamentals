package org.example.cryptography.hashing;

import org.example.cryptography.Utils.CryptoUtils;
import org.example.cryptography.Utils.EncodeUtils;
import org.example.cryptography.Utils.HashUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;

public class PBKDF2Test {

    /**
     * SHA-256 is not enough for password hashing even using salt that is the fundamental for password hashing.
     * PBKDF2 applies a pseudorandom function, such as hash-based message authentication code (HMAC), to the
     * input password or passphrase along with a salt value and repeats the process many times to produce a
     * derived key, which can then be used as a cryptographic key in subsequent operations. The added computational
     * work makes password cracking much more difficult, and is known as key stretching.
     * PBKDF2, BCrypt, and SCrypt. BCrypt, and SCrypt does not ship with Java SDK by default
     */
    public static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA1";

    public static final int ITERATION_COUNT = 65536;

    public static final int KEY_LENGTH = 128;

    @Test
    @DisplayName("Generated hash using PBKDF2 for Password hashing")
    public void hashWithPBKDF2() throws GeneralSecurityException {

        final var passwordClear = "myPassword";

        System.out.println("Generate password hash using PBKDF2 algorithm using salt and iterations");
        final var salt1 = CryptoUtils.getRandomNonce();
        final var hash1 = hash(passwordClear, salt1);
        System.out.println(hash1);

        System.out.println("Generate same password hash using PBKDF2 algorithm using salt and iterations");
        final var salt2 = CryptoUtils.getRandomNonce();
        final var hash2 = hash(passwordClear, salt2);
        System.out.println(hash2);

        System.out.println("Generate same password hash using PBKDF2 algorithm using same previous salt and same iterations");
        final var hash3 = hash(passwordClear, salt2);
        System.out.println(hash3);
    }

    private HashUtils.HashData hash(final String password, final byte[] salt) throws GeneralSecurityException {
        final var spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        final var factory = SecretKeyFactory.getInstance(HASH_ALGORITHM);
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return new HashUtils.HashData(password.getBytes(), hash, EncodeUtils.encode(hash));
    }

}
