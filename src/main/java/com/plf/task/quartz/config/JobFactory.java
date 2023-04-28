package com.plf.task.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public  class JobFactory extends AdaptableJobFactory {

    @Resource
    private AutowireCapableBeanFactory  capableBeanFactory;

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        log.info("进行注入实例对象 : {}",jobInstance);
        // 可以进行Spring Bean的注入
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}