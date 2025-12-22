package com.wddyxd.fileservice.quartZ;


import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wddyxd.fileservice.mapper.UserFileBindMapper;
import com.wddyxd.fileservice.pojo.entity.UserFileBind;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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

    // 文件存储根路径（本地文件系统，Demo用）
    private static final String fileStoragePath="E:/FILE_STORAGE/";

    @Autowired
    private UserFileBindMapper userFileBindMapper;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        try{
            System.out.println("开始执行废弃文件清理任务...");

            // 1. 查询MySQL中所有无效文件（is_valid=0）
            LambdaQueryWrapper<UserFileBind> invalidQuery = new LambdaQueryWrapper<>();
            invalidQuery.eq(UserFileBind::getIsValid, 0)
                    .eq(UserFileBind::getIsDeleted, false);
            List<UserFileBind> invalidFileBinds = userFileBindMapper.selectList(invalidQuery);
            // 提取无效文件名 + 用户ID，构造文件路径
            for (UserFileBind bind : invalidFileBinds) {
                String filePath = fileStoragePath + File.separator + bind.getUserId() + File.separator + bind.getFileName();
                FileUtil.del(filePath); // 删除文件
                // 可选：删除MySQL无效记录（或保留归档）
                bind.setIsDeleted(true);
                userFileBindMapper.updateById(bind);
            }
            System.out.println("清理MySQL无效文件：" + invalidFileBinds.size() + " 个");

            // 2. 扫描存储目录，删除无MySQL绑定的文件
            File rootDir = new File(fileStoragePath);
            if (!rootDir.exists()) {
                System.out.println("文件存储目录不存在，无需扫描");
                return;
            }
            // 获取所有用户目录
            File[] userDirs = rootDir.listFiles(File::isDirectory);
            if (userDirs == null || userDirs.length == 0) {
                System.out.println("无用户目录，无需扫描");
                return;
            }

            // 遍历每个用户目录
            for (File userDir : userDirs) {
                String userId = userDir.getName();
                // 查询该用户有效绑定的文件名
                LambdaQueryWrapper<UserFileBind> validQuery = new LambdaQueryWrapper<>();
                validQuery.eq(UserFileBind::getUserId, userId)
                        .eq(UserFileBind::getIsValid, 1);
                List<UserFileBind> validFileBinds = userFileBindMapper.selectList(validQuery);
                List<String> validFileNames = validFileBinds.stream()
                        .map(UserFileBind::getFileName)
                        .collect(Collectors.toList());

                // 遍历用户目录下的所有文件
                File[] userFiles = userDir.listFiles(File::isFile);
                if (userFiles == null || userFiles.length == 0) {
                    continue;
                }
                int cleanCount = 0;
                for (File userFile : userFiles) {
                    String fileName = userFile.getName();
                    // 若文件不在有效绑定列表中，删除
                    if (!validFileNames.contains(fileName)) {
                        FileUtil.del(userFile);
                        cleanCount++;
                    }
                }
                System.out.println("用户 " + userId + " 清理无绑定文件：" + cleanCount + " 个");
            }

            System.out.println("废弃文件清理任务执行完成！");

        }catch (Exception e){
            System.err.println("废弃文件清理任务执行失败：" + e.getMessage());
            e.printStackTrace();
            throw new JobExecutionException(e);
        }

    }

}
