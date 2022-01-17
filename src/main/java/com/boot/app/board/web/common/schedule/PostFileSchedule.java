package com.boot.app.board.web.common.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PostFileSchedule {

    @Scheduled(fixedDelay = 2000)
    public void task1(){
        System.out.println("PostFileSchedule.task1");
    }
}
