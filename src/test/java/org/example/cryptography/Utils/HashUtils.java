package org.example.cryptography.Utils;

public class HashUtils {

    public record HashData(byte[] data, byte[] hash, String base64) {
        public String toString() {
            return String.format("""
                        data: %s
                        hash: %s
                        base64: %s
                        (size: "%s", bits: "%s")
                    """, EncodeUtils.toString(this.data), EncodeUtils.toHex(hash),
                this.base64, this.hash.length, this.hash.length * 8);
        }
    }
}
