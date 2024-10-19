package org.example.cryptography.asymmetric;

import org.example.cryptography.Utils.CryptoUtils;
import org.example.cryptography.Utils.EncodeUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.example.cryptography.Utils.CryptoUtils.RSA;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RSASignTest {

    public static final String SIGN_ALGORITHM = "SHA256withRSA";

    @Test
    @DisplayName("Generate Digital Signature to ensure confidentiality and integrity using RSA.")
    public void signWithRSAMustBeValid() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var keyPair = CryptoUtils.generateAsymmetricKeyPair();

        // The data to be sign
        final var data = "This is an example that uses RSA to sign.";
        System.out.printf("Data: %s\n", data);

        // Sign data with the private key
        final var signature = sign(data.getBytes(), keyPair.getPrivate().getEncoded());
        System.out.printf("Signature: %s\n", EncodeUtils.toHex(signature));

        // Validate signature against the data and public key.
        final var valid = validate(data.getBytes(), keyPair.getPublic().getEncoded(), signature);
        System.out.printf("Valid: %s\n", valid);

        assertTrue(valid);
    }

    @Test
    @DisplayName("Generate Digital Signature to ensure confidentiality and integrity using RSA must be invalid.")
    public void signWithRSAMustBeInvalid() throws GeneralSecurityException {

        // Generate the Symmetric key
        final var keyPair = CryptoUtils.generateAsymmetricKeyPair();

        // The data to be sign
        final var data = "This is an example that uses RSA to sign.";
        System.out.printf("Data: %s\n", data);

        // Sign data with the private key
        final var signature = sign(data.getBytes(), keyPair.getPrivate().getEncoded());
        System.out.printf("Signature: %s\n", EncodeUtils.toHex(signature));

        // The data to be sign
        final var dataMod = "This is another example that uses RSA to sign to test the validation.";
        System.out.printf("Data Modified: %s\n", dataMod);

        // Validate signature against the data and public key.
        final var valid = validate(dataMod.getBytes(), keyPair.getPublic().getEncoded(), signature);
        System.out.printf("Valid: %s\n", valid);

        assertFalse(valid);
    }

    private static byte[] sign(final byte[] data, final byte[] privateKey) throws GeneralSecurityException {
        // Get the private key encoded
        final var keyFactory = KeyFactory.getInstance(RSA);
        final var privateKeySpec = new PKCS8EncodedKeySpec(privateKey);
        final var privateKeyCipher = keyFactory.generatePrivate(privateKeySpec);

        // Fill the information to be signed with the data and private key.
        final var signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(privateKeyCipher);
        signature.update(data);

        // Sign the data with the private key.
        return signature.sign();
    }

    private static boolean validate(final byte[] data, final byte[] publicKey, final byte[] signature) throws GeneralSecurityException {
        // Get the public key encoded
        final var keyFactory = KeyFactory.getInstance(RSA);
        final var publicKeySpec = new X509EncodedKeySpec(publicKey);
        final var publicKeyCipher = keyFactory.generatePublic(publicKeySpec);

        // Fill the information to be signed with the data and public key.
        final var verifier = Signature.getInstance(SIGN_ALGORITHM);
        verifier.initVerify(publicKeyCipher);
        verifier.update(data);

        // Verify the data with signature and public key.
        return verifier.verify(signature);
    }


}
