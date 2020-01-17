package com.qf.timer.jdk;

import java.util.Date;
import java.util.TimerTask;

/**
 * @Author chenzetao
 * @Date 2020/1/14
 */
public class MyTask extends TimerTask {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("now: "+new Date());
    }
}
