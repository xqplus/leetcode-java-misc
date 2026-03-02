package io.github.xqplus.virt.winstack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * CNware WinStack 虚拟化平台访问接口
 *
 * @author chenq
 * @since 2.7.0
 */
public class WinStack implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(WinStack.class);

    private final String urlPrefix;
    private final JSONObject loginBody;
    private final RestApiClient restApiClient;
    private TokenAware tokenAware;

    public WinStack(String ip, String user, String pwd, Integer type) throws GeneralSecurityException {
        urlPrefix = "https://" + ip;
        loginBody = new JSONObject();
        loginBody.put("user", user);
        loginBody.put("pwd", pwd);
        loginBody.put("type", type);

        restApiClient = new RestApiClient((request, body, execution) -> {
            LOGGER.info("{}, {}", request.getURI(), new String(body));
            if (!"/api/login".equals(request.getURI().getPath())) {
                request.getHeaders().set("cookie", "SESSION=" + getToken());
            }
            return execution.execute(request, body);
        });
    }

    private String getToken() {
        if (tokenAware == null || tokenAware.isExpired()) {
            JSONObject loginResult = login();
            tokenAware = new TokenAware(loginResult.getString("sessionId"), loginResult.getLong("sessionTimeOut"), TimeUnit.SECONDS);
        }
        return tokenAware.getToken();
    }

    public JSONObject login() {
        return restApiClient.post(urlPrefix + "/api/login", loginBody, JSONObject.class);
    }

    public void logout() {
        restApiClient.post(urlPrefix + "/api/logout", null, String.class);
    }

    public JSONObject getPoolById(String poolId) {
        return restApiClient.get(urlPrefix + "/api/pools/" + poolId, JSONObject.class);
    }

    public String getVms() {
        return restApiClient.get(urlPrefix + "/api/about/us/product", String.class);
    }

    @Override
    public void close() throws Exception {
        if (restApiClient != null) {
            if (tokenAware != null && !tokenAware.isExpired()) logout();
            restApiClient.close();
        }
    }
}
