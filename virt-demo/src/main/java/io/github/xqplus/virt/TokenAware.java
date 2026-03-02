package io.github.xqplus.virt;

import java.util.concurrent.TimeUnit;

public class TokenAware {
    // token有效期，登录接口未返回有效期，经验证实际会超过两小时，暂按照平台会话默认值设置
    private static final long EXPIRE_TIME = TimeUnit.MINUTES.toMillis(120);
    private final String token;
    private final long createTimeMillis;

    public TokenAware(String token) {
        this.token = token;
        createTimeMillis = System.currentTimeMillis();
    }

    public String getToken() {
        return token;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - createTimeMillis > EXPIRE_TIME;
    }
}
