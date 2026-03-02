package io.github.xqplus.virt.winstack;

import java.util.concurrent.TimeUnit;

public class TokenAware {

    private final String token;
    private final long expireTimeMillis;

    public TokenAware(String token, long timeout, TimeUnit unit) {
        this.token = token;
        expireTimeMillis = System.currentTimeMillis() + unit.toMillis(timeout);
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expireTimeMillis;
    }

    public String getToken() {
        return token;
    }
}
