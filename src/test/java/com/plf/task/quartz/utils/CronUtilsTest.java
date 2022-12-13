package com.plf.task.quartz.utils;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author panlf
 * @date 2021/8/23
 */
public class CronUtilsTest {

    @Test
    public void test(){
        List<Date> list = CronUtils.getExecuteTimes("0 0/10 * * * ?",5);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        list.forEach(x-> System.out.println(simpleDateFormat.format(x)));
    }
}
