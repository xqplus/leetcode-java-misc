package com.github.xqplus.sample.controller;

import com.alibaba.fastjson2.JSONObject;
import com.github.xqplus.sample.common.R;
import com.github.xqplus.sample.common.TransportMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SampleController {

    @GetMapping("/t1")
    public R t1() {
        List<JSONObject> transportMethods = new ArrayList<>();
        for (TransportMethod transportMethod : TransportMethod.values()) {
            JSONObject transportMethodJo = new JSONObject();
            transportMethodJo.put("id", transportMethod.getCode());
            transportMethodJo.put("name", transportMethod.getName());
            transportMethods.add(transportMethodJo);
        }
        return R.ok().data(transportMethods);
    }

    @GetMapping("/t2")
    public void t2() {
        Path byPathDir = Paths.get("/dev/disk/by-path");
        try (DirectoryStream<Path> byPathPaths = Files.newDirectoryStream(byPathDir)) {
            Path byPath = byPathPaths.iterator().next();
            System.out.println(byPath.toAbsolutePath());
            System.out.println("符号链接：" + Files.isSymbolicLink(byPath));
            Path path = Files.readSymbolicLink(byPath);
            System.out.println(path);
            System.out.println(Files.isSymbolicLink(path));
            Path resolve = byPath.resolve(path);
            System.out.println(resolve);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @GetMapping("/t3")
    public void t3() {

    }
}
