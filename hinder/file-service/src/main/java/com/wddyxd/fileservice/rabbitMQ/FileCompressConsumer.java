package com.wddyxd.fileservice.rabbitMQ;


import com.wddyxd.common.constant.CommonConstant;
import com.wddyxd.fileservice.service.impl.IUserFileBindServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @program: fileOperator
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-22 14:48
 **/
@Component
public class FileCompressConsumer {

    //TODO 要实现一个RabbitMQ异常捕捉器

    private static final Logger log = LoggerFactory.getLogger(FileCompressConsumer.class);

    @RabbitListener(queuesToDeclare = @Queue(
        name = "${rabbitmq.queue.compress:file-compress-queue}", // 保留配置读取+默认值
        durable = "true",
        exclusive = "false",
        autoDelete = "false"
))
    public void handleFileCompress(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                log.error("图片不存在：{}", filePath);
                return;
            }

            // 压缩图片（保持宽高比，压缩质量）
            Thumbnails.of(file)
                    .scale(1.0) // 不改变尺寸
                    .outputQuality(CommonConstant.COMPRESS_QUALITY) // 压缩质量
                    .toFile(file); // 覆盖原文件

            log.info("图片压缩成功：{}", filePath);
        } catch (Exception e) {
            log.error("图片压缩失败：{}，异常：{}", filePath, e.getMessage());
            e.printStackTrace();
        }
    }

}
