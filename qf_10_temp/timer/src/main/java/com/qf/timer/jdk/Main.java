package com.qf.timer.jdk;

import java.util.Date;
import java.util.Timer;

/**
 * @Author chenzetao
 * @Date 2020/1/14
 */
public class Main {
    public static void main(String[] args){
        Timer timer = new Timer();
        MyTask task = new MyTask();

        System.out.println(new Date());
        //执行任务
//        timer.schedule(task,3000);

        //重复做一件事情
        timer.schedule(task,0,2000);

        //需要改进的地方
        //单线程的执行方式
        //时间不固定就不好办
    }
}
