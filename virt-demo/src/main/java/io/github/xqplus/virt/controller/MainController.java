package io.github.xqplus.virt.controller;

import io.github.xqplus.virt.configuration.AppConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
@RestController("/main")
public class MainController {

    private final AppConfiguration appConfiguration;

    @PostConstruct
    public void init() {
        log.info("enter post construct");

        log.info("checkSnapshotEnabled: {}", appConfiguration.isCheckSnapshotEnabled());

        log.info("leave post construct");
    }
}
