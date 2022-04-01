package com.lytquest.frogdemo.helper;

import com.lytquest.frogdemo.service.impl.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleTask {

    @Autowired
    private BookServiceImpl service;

    @Scheduled(cron="0 14 4 * * *")
    public void doScheduleJob(){
        service.readDataAsync();
    }
}
