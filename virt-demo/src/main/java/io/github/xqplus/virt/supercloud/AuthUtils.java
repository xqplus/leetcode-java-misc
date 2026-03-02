package io.github.xqplus.virt.supercloud;

/**
 * @author zouxiangbin
 */
public class AuthUtils {
    /**
     * 这里生成52byte的认证码  加锁的方法
     * 包含crc32算法   sha256算法
     * @param rdByte       随机值转的4位byte
     * @param model_name   模块名  16位byte
     * @param vBytes       版本     4位byte
     * @return
     */
    public synchronized static byte[] getAuthPass(byte[] rdByte,String model_name,byte[] vBytes){
        byte[] b52 = new byte[52];
        // 先把填充前24位
        full24(b52,rdByte,model_name,vBytes);
        // 取出前24位
        byte[] b24 = getFirst24(b52);
        int crc32 = Crc32Utils.my_crc32_1(0, b24, 24);
        byte[] crc32ToB4 = new byte[4];
        LenUtils.intToBytes(crc32,crc32ToB4);
        // 计算好的crc32填充回去
        full24_2(b52,crc32ToB4);
        // 再获取前24位
        b24 = getFirst24(b52);
        String sha256StrJava = SHA256Utils.getSHA256StrJava(b24);
        byte[] sha256_32 = new byte[32];
        for (int i = 0,j = 0; i < sha256StrJava.length(); i+=2,j+=1) {
            String any = sha256StrJava.substring(i,i+2);
            sha256_32[j] = (byte) Integer.parseInt(any, 16);
        }
        //将这32位填充到后32位去
        fullLast32(b52,sha256_32);

        return b52;
    }

    /**
     * 填充后面的32位
     * @param b52
     * @param sha256_32
     */
    private static void fullLast32(byte[] b52, byte[] sha256_32) {
        for (int i = 0; i < 32; i++) {
            b52[i+20] = sha256_32[i];
        }
    }

    /**
     * 覆盖随机数的那4位
     * @param b52
     * @param crc32ToB4
     */
    private static void full24_2(byte[] b52, byte[] crc32ToB4) {
        for (int i = 0; i < 4; i++) {
            b52[i+20] = crc32ToB4[i];
        }
    }

    /**
     * 获取前24位给crc32使用
     * @param b52
     * @return
     */
    private static byte[] getFirst24(byte[] b52) {
        byte[] b24 = new byte[24];
        for (int i = 0; i < 24; i++) {
            b24[i] = b52[i];
        }
        return b24;
    }

    /**
     * 第一步骤，填充前24位
     * @param b52
     * @param rdByte
     * @param model_name
     * @param vBytes
     */
    private static void full24(byte[] b52, byte[] rdByte, String model_name, byte[] vBytes) {
        byte[] m_name = model_name.getBytes();
        for (int i = 0; i < m_name.length; i++) {
            b52[i] = m_name[i];
        }
        for (int i = 0; i < vBytes.length; i++) {
            b52[i+16] = vBytes[i];
        }
        for (int i = 0; i < rdByte.length; i++) {
            b52[i+20] = rdByte[i];
        }
    }

}
