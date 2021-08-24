package com.plf.task.quartz.utils;

import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author panlf
 * @date 2021/8/23
 */
public class CronUtils {
    public static Date getExecuteTime(String cron, Date date) {
        if (StringUtils.isEmpty(cron))
            throw new IllegalArgumentException("cron表达式不可为空");
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        return cronSequenceGenerator.next(date);
    }

    public static Date getExecuteTimeByNow(String cron) {
        return getExecuteTime(cron, new Date());
    }

    public static List<Date> getExecuteTimes(String cron, Integer count) {
        if (StringUtils.isEmpty(cron))
            throw new IllegalArgumentException("cron表达式不可为空");
        count = count==null||count<1?1:count;
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        List<Date> list = new ArrayList<Date>(count);
        Date nextTimePoint = new Date();
        for (int i = 0; i < count; i++) {
            // 计算下次时间点的开始时间
            nextTimePoint = cronSequenceGenerator.next(nextTimePoint);
            list.add(nextTimePoint);
        }
        return list;
    }
}
