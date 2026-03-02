package io.github.xqplus.virt.controller;

import io.github.xqplus.virt.configuration.AppConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URISyntaxException;
import java.security.CodeSource;

@Slf4j
@AllArgsConstructor
@RestController("/main")
public class MainController {

    public static String JAR_DIR;

    static {
//        ApplicationHome home = new ApplicationHome(MainController.class);
//        JAR_DIR = home.getSource().getParentFile().toString();
        CodeSource codeSource = MainController.class.getProtectionDomain().getCodeSource();
        try {
            String path = codeSource.getLocation().toURI().getPath();
            JAR_DIR = new File(path).getParentFile().getAbsolutePath();
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
        }

    }

    private final AppConfiguration appConfiguration;

    @PostConstruct
    public void init() {
        log.info("enter post construct");

        log.info("checkSnapshotEnabled: {}", appConfiguration.isCheckSnapshotEnabled());

        log.info("leave post construct");
    }

    @GetMapping("jar-dir")
    public String jarDir() {
        return JAR_DIR;
    }
}
