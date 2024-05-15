package com.github.xqplus.sample.reflect;

import com.alibaba.fastjson2.JSONObject;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        JSONObject jo = new JSONObject();
        jo.put("path", "/storage/mnt/35");
        jo.put("os", "linux");
        System.out.println(jo.toString());
    }
}
