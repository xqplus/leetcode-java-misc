package com.github.xqplus.sample.common;

public class LenUtils {
    /**
     * byte转int
     * @param src
     * @param offset
     * @return
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int)(((src[offset] & 0xFF)<<24)

                |((src[offset+1] & 0xFF)<<16)

                |((src[offset+2] & 0xFF)<<8)

                |(src[offset+3] & 0xFF));
        return value;
    }

    /**
     * int转byte
     * @param value
     * @param src
     */
    public static void intToBytes(int value, byte[] src){
        src[0] = (byte) ((value>>24) & 0xFF);
        src[1] = (byte) ((value>>16)& 0xFF);
        src[2] = (byte) ((value>>8)&0xFF);
        src[3] = (byte) (value & 0xFF);
    }
}
