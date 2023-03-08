package com.arthor.core.common.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public abstract class TimeUtils {

    /**
     * 判断当前时间与time的间隔是否超过了intervalMs
     *
     * @param time
     * @param interval
     * @return
     */
    public static boolean isAfter(LocalDateTime time, long interval) {
        time = time.plusSeconds(interval);
        Date date = Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
        return date.getTime() <= new Date().getTime();
    }

}
