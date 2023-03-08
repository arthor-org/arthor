package com.arthor.server.scheduler;

import com.arthor.server.facade.DeploymentFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@RequiredArgsConstructor
public class DeployCheckScheduler {

    private final DeploymentFacade deploymentFacade;

    private volatile boolean started = false;

    /**
     * 检查,这里有点忘了,Scheduled是多线程调度还是单线程,加个标识保险
     * 后面替换成xxlJob即可,无需在意
     */
    @Scheduled(fixedDelay = 10 * 1000)
    public void check() {
        if (!started) {
            started = true;
        }
        deploymentFacade.check();
        started = false;
    }

}
