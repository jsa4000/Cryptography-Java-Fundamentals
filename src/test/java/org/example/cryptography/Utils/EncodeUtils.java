package org.example.cryptography.Utils;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncodeUtils {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static final int CHUNK_SIZE = 16;

    public static String encode(final byte[] data) {
        return new String(Base64.getEncoder().encode(data), DEFAULT_CHARSET);
    }

    public static String decode(final byte[] data) {
        return new String(Base64.getDecoder().decode(data), DEFAULT_CHARSET);
    }

    public static String toString(final byte[] data) {
        return new String(data, DEFAULT_CHARSET);
    }

    public static String toHex(final byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static String toHexSplit(final byte[] bytes, final int chunkSize) {
        final var textArray = EncodeUtils.toHex(bytes).split(String.format("(?<=\\G.{%d})", chunkSize));
        return String.join("\n", textArray);
    }

    public static String toBinaryStringWithPadding(final int i, final int size) {
        return StringUtils.leftPad(Integer.toBinaryString(i), size, '0');
    }

    public static String toStringByteFromInt(final int number) {
        return new String(new byte[]{(byte) number}, StandardCharsets.US_ASCII);
    }

}

