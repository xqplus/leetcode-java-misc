package io.github.xqplus.virt.winstack.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginBody {

    private String user;
    private String pwd;

    /**
     * 用户类型（1：本地用户 2：AD域用户）,默认1 本地用户
     */
    private int type;

    /**
     * 	UKey模式 None:禁用 Datech:得安 Fisec:渔翁
     */
    private String modeUKey;

    /**
     * 验证数据
     */
    private String verifyData;

    /**
     * 验证码
     */
    private String imgCode;

    /**
     * 证书的base64
     */
    private String cerBase64;

    /**
     * 	new证书的base64
     */
    private String newCerBase64;

    /**
     * 登陆成功后回调地址
     */
    private String redirectUrl;

    /**
     * 登录数据标识
     */
    private String loginData;

    public LoginBody(String user, String pwd) {
        this(user, pwd, 1);
    }

    public LoginBody(String user, String pwd, int type) {
        this.user = user;
        this.pwd = pwd;
        this.type = type;
    }
}
