package com.qf.timer.boot;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author chenzetao
 * @Date 2020/1/14
 */
@Component
public class BootSechedul {

//    @Scheduled(fixedDelay = 3000)
    @Scheduled(cron = "0/3 * * * * ? ")
    public void run(){
        System.out.println(Thread.currentThread().getName());
        System.out.println("now: "+new Date());
    }
}
