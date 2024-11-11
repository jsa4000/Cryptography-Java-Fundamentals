package org.example.cryptography.hashing;

import org.example.cryptography.Utils.EncodeUtils;
import org.example.cryptography.Utils.HashUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HMACTest {

    // --8<-- [start:crypto-values]

    /**
     * HMAC is a cryptographic method that guarantees the integrity of the message between two parties.
     * HMAC algorithm consists of a secret key and a hash function (SHA-256). The secret key is a unique piece
     * of information or a string of characters. It is known both by the sender and the receiver of the message.
     * The hash function is a mapping algorithm that converts one sequence to another sequence.
     */
    public static final String HASH_ALGORITHM = "HmacSHA256";

    // --8<-- [end:crypto-values]

    @Test
    @DisplayName("Generated hash using HMAC for validate the integrity of the hashing")
    public void hashWithHMAC() throws GeneralSecurityException {

        final var sharedKey = "mySharedKey";
        final var data = "My data";

        System.out.println("Source generates HMAC using shared key and send both data and hmac");
        final var hmacSrc = hmac(data.getBytes(), sharedKey.getBytes());
        System.out.println(hmacSrc);

        System.out.println("Destination generates HMAC using shared key and compare it with the original");
        final var hmacDst = hmac(data.getBytes(), sharedKey.getBytes());
        System.out.println(hmacDst);

        assertEquals(hmacSrc.base64(), hmacDst.base64());
    }

    @Test
    @DisplayName("Generated hash using HMAC for authorize the integrity of the hashing that is not valid")
    public void hashWithHMACNotValid() throws GeneralSecurityException {

        final var sharedKey = "mySharedKey";

        System.out.println("Source generates HMAC using shared key and send both data and hmac");
        final var dataSrc = "My data";
        final var hmacSrc = hmac(dataSrc.getBytes(), sharedKey.getBytes());
        System.out.println(hmacSrc);

        System.out.println("Destination generates HMAC using shared key and compare it with the original");
        final var dataDst = "My data tampered";
        final var hmacDst = hmac(dataDst.getBytes(), sharedKey.getBytes());
        System.out.println(hmacDst);

        System.out.println("Data has been tampered at flight since the hmac does not match each other");

        assertNotEquals(hmacSrc.base64(), hmacDst.base64());
    }

    // --8<-- [start:hash]

    private HashUtils.HashData hmac(final byte[] data, final byte[] key) throws GeneralSecurityException {
        final var spec = new SecretKeySpec(key, HASH_ALGORITHM);
        final var mac = Mac.getInstance(HASH_ALGORITHM);
        mac.init(spec);
        final var hmac = mac.doFinal(data);
        return new HashUtils.HashData(data, hmac, EncodeUtils.encode(hmac));
    }

    // --8<-- [end:hash]

}
