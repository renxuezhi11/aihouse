package com.aihouse.aihousesys.config;

import com.aihouse.aihouseservice.QuartzService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//实现CommandLineRunner接口，以达到开机启动执行
@Component
public class JobSchedule implements CommandLineRunner {

    @Resource
    private QuartzService quartzService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("任务调度开始==============任务调度开始");
        quartzService.timingTask();
        System.out.println("任务调度结束==============任务调度结束");
    }
}
