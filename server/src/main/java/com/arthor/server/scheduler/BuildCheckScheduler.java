package com.arthor.server.scheduler;

import com.arthor.server.facade.BuildFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class BuildCheckScheduler {

    private final BuildFacade buildFacade;

    /**
     * 构建信息检查
     */
    @Scheduled(fixedDelay = 10 * 1000)
    public void checkBuildInfo() {
        buildFacade.checkBuildInfo();
    }

}
