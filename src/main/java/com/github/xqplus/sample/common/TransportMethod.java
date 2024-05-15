package com.github.xqplus.sample.common;

/**
 * 备份数据传输方法
 *
 * @since 2.1.0
 */
public enum TransportMethod {

    NBD(1, "nbd", "网络传输（LAN）"),
    NBD_SSL(2, "nbdssl", "网络加密传输（LAN + SSL）"),
    HOT_ADD(3, "hotadd", "热添加传输（HotAdd）"),
    SAN(4, "san", "LAN-Free传输（SAN）");

    private final int code;
    private final String method;
    private final String name;

    TransportMethod(int code, String method, String name) {
        this.code = code;
        this.method = method;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TransportMethod resolve(Integer code) {
        for (TransportMethod transportMethod : values()) {
            if (transportMethod.code == code) {
                return transportMethod;
            }
        }
        return null;
    }

    public boolean equals(Integer code) {
        return code != null && code == this.code;
    }
}
