package org.example.cryptography.Utils;

import java.security.SecureRandom;
import java.util.Random;

public class XorUtils {

    /**
     * @param bits
     * @return
     */
    public static byte[] generateKey(final int bits) {
        SecureRandom random = new SecureRandom();
        // bits are converted into bytes. i.e 128bits / 8 bits = 16 bytes
        var result = new byte[(int) Math.floor((double) bits / 8)];
        // Generate a random value using specified bytes size.
        random.nextBytes(result);
        return result;
    }

    /**
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumberInRange(final int min, final int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        final var random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    /**
     * Apply XOR operation to an array, arrays must be equal in size.
     *
     * @param string1
     * @param string2
     * @return
     */
    public static byte[] xor(final byte[] string1, final byte[] string2) {
        if (string1.length != string2.length) {
            throw new RuntimeException("Both byte arrays must be equal to apply XOR operation");
        }
        byte[] out = new byte[string1.length];
        for (int i = 0; i < string1.length; i++) {
            out[i] = (byte) (string1[i] ^ string2[i % string2.length]);
        }
        return out;
    }
}
