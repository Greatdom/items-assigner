package com.wddyxd.fileservice.quartZ;


import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: fileOperator
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-22 15:01
 **/

@Configuration
public class QuartzConfig {
    // 任务组名
    private static final String JOB_GROUP_NAME = "FILE_CLEAN_JOB_GROUP";
    // 触发器组名
    private static final String TRIGGER_GROUP_NAME = "FILE_CLEAN_TRIGGER_GROUP";

    /**
     * 配置清理任务
     */
    @Bean
    public JobDetail fileCleanJobDetail() {
        return JobBuilder.newJob(FileCleanJob.class)
                .withIdentity("FileCleanJob", JOB_GROUP_NAME)
                .storeDurably() // 任务持久化
                .build();
    }

    /**
     * 配置触发器（每天凌晨2点执行）
     */
    @Bean
    public Trigger fileCleanTrigger() {
        // Cron表达式：0 0 2 * * ? （每天凌晨2点）
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 2 * * ?");

        return TriggerBuilder.newTrigger()
                .withIdentity("FileCleanTrigger", TRIGGER_GROUP_NAME)
                .forJob(fileCleanJobDetail())
                .withSchedule(cronScheduleBuilder)
                .build();
    }
}