package com.zzw.springboot11task.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {


    /**
     *  秒、分、时、日、月、周几
     *  0  *  *  *  *  MON-FRI
     */

    // @Scheduled(cron = "0 * * * * MON-FRI")
    // @Scheduled(cron = "0,1,2,3,4 * * * * MON-FRI")
    // @Scheduled(cron = "0-4 * * * * MON-FRI") // 0至4秒都执行以下方法
    @Scheduled(cron = "0/4 * * * * MON-FRI")  // 每4秒执行一次
    public void hello() {
        System.out.println("hello ...");
    }
}
