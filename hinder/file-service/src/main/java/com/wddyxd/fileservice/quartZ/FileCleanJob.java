package com.wddyxd.fileservice.quartZ;


import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wddyxd.fileservice.mapper.UserFileBindMapper;
import com.wddyxd.fileservice.pojo.entity.UserFileBind;
import com.wddyxd.fileservice.service.impl.IUserFileBindServiceImpl;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: fileOperator
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-22 14:51
 **/

public class FileCleanJob extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(FileCleanJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        log.info("触发定期清理不绑定的文件和没被数据库引用的文件的任务...");

    }

}
