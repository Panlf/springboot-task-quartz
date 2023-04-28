package com.plf.task.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author panlf
 * @date 2023/4/28
 */
@Service
@Slf4j
public class HelloService {
    public void sayHello(){
        log.info("HelloService say hello");
    }
}
