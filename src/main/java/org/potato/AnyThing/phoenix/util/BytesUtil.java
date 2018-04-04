package org.potato.AnyThing.phoenix.util;

import java.nio.charset.Charset;

/**
 * Created by potato on 2018/4/4.
 */
public class BytesUtil {
    /** When we encode strings, we always specify UTF8 encoding */
    private static final String UTF8_ENCODING = "UTF-8";
    /** When we encode strings, we always specify UTF8 encoding */
    private static final Charset UTF8_CHARSET = Charset.forName(UTF8_ENCODING);
    /**
     * Convert an int value to a byte array.  Big-endian.  Same as what DataOutputStream.writeInt
     * does.
     *
     * @param val value
     * @return the byte array
     */
    public static byte[] toBytes(int val) {
        byte [] b = new byte[4];
        for(int i = 3; i > 0; i--) {
            b[i] = (byte) val;
            val >>>= 8;
        }
        b[0] = (byte) val;
        return b;
    }
    /**
     * Convert a long value to a byte array using big-endian.
     *
     * @param val value to convert
     * @return the byte array
     */
    public static byte[] toBytes(long val) {
        byte [] b = new byte[8];
        for (int i = 7; i > 0; i--) {
            b[i] = (byte) val;
            val >>>= 8;
        }
        b[0] = (byte) val;
        return b;
    }
    /**
     * Converts a string to a UTF-8 byte array.
     * @param s string
     * @return the byte array
     */
    public static byte[] toBytes(String s) {
        return s.getBytes(UTF8_CHARSET);
    }
}

