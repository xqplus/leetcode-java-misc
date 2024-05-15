package com.github.xqplus.sample.common;

import java.util.HashMap;

/**
 * 返回结果封装
 *
 * @author chenq
 */
public class R extends HashMap<String, Object> {

    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";
    public static final String TOTAL = "total";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    /**
     * 构造方法私有化
     *
     * @param code    响应码
     * @param message 响应消息
     */
    private R(Integer code, String message) {
        put(CODE, code);
        put(MESSAGE, message);
    }

    /**
     * 成功响应
     *
     * @param code 响应码
     * @param msg  响应消息
     * @return {@link R}
     */
    public static R ok(Integer code, String msg) {
        return new R(code, msg);
    }

    /**
     * 失败响应
     *
     * @param code 响应码
     * @param msg  响应消息
     * @return {@link R}
     */
    public static R err(Integer code, String msg) {
        return new R(code, msg);
    }

    /**
     * 成功响应（响应码200）
     *
     * @param msg 响应消息
     * @return {@link R}
     */
    public static R ok(String msg) {
        return ok(200, msg);
    }

    /**
     * 成功响应（响应码200 响应消息success）
     *
     * @return {@link R}
     */
    public static R ok() {
        return ok(SUCCESS);
    }

    /**
     * 失败响应（响应码999）
     *
     * @param msg 响应消息
     * @return {@link R}
     */
    public static R err(String msg) {
        return err(999, msg);
    }

    /**
     * 失败响应（响应码999 响应消息error）
     *
     * @return {@link R}
     */
    public static R err() {
        return err(ERROR);
    }

    /**
     * 携带响应数据
     *
     * @param data 响应数据
     * @return {@link R}
     */
    public R data(Object data) {
        put("data", data);
        return this;
    }

//    public R page(IPage<?> page) {
//        put(DATA, page.getRecords());
//        put(TOTAL, page.getTotal());
//        return this;
//    }

    /**
     * 添加返回数据
     *
     * @param key 数据键
     * @param val 数据值
     * @return {@link R}
     */
    public R and(String key, Object val) {
        put(key, val);
        return this;
    }

//    public static <T> T procCallback(String callback, Class<T> clazz) {
//        Assert.notNull(callback, "Http result is null");
//
//        JSONObject jo = JSON.parseObject(callback);
//        if (jo.getInteger(CODE) == 401) {
//            return jo.getObject(DATA, clazz);
//        }
//        if (jo.getInteger(CODE) != 200) {
//            throw new ServiceException(jo.getString(MESSAGE));
//        }
//        return jo.getObject(DATA, clazz);
//    }
}
