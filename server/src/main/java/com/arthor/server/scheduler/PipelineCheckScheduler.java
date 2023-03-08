package com.arthor.server.scheduler;

import com.arthor.server.facade.ApplicationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class PipelineCheckScheduler {

    private final ApplicationFacade applicationFacade;

    /**
     * 流水线信息检查
     */
    @Scheduled(fixedDelay = 10 * 1000)
    public void checkPipelineInfo() {
        applicationFacade.checkPipelineInfo();
    }
}
