package com.wddyxd.fileservice.rabbitMQ;


import net.coobird.thumbnailator.Thumbnails;
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

    // 图片压缩质量（0-1，数值越小压缩率越高，失真越严重）
    private static final float COMPRESS_QUALITY = 0.5f;

//    @RabbitListener(queues = "${rabbitmq.queue.compress:file-compress-queue}")
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
                System.err.println("图片不存在：" + filePath);
                return;
            }

            // 压缩图片（保持宽高比，压缩质量）
            Thumbnails.of(file)
                    .scale(1.0) // 不改变尺寸
                    .outputQuality(COMPRESS_QUALITY) // 压缩质量
                    .toFile(file); // 覆盖原文件

            System.out.println("图片压缩成功：" + filePath);
        } catch (Exception e) {
            System.err.println("图片压缩失败：" + filePath + "，异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

}
