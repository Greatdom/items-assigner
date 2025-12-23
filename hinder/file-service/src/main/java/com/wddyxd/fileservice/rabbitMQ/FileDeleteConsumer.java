package com.wddyxd.fileservice.rabbitMQ;


import cn.hutool.core.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @program: fileOperator
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-22 14:50
 **/

@Component
public class FileDeleteConsumer {

    private static final Logger log = LoggerFactory.getLogger(FileDeleteConsumer.class);

    @RabbitListener(queuesToDeclare = @Queue(
            name = "${rabbitmq.queue.delete:file-delete-queue}", // 保留配置读取+默认值
            durable = "true",
            exclusive = "false",
            autoDelete = "false"
    ))
    public void handleFileDelete(String filePath){
        try {
            // 删除文件
            boolean delete = FileUtil.del(filePath);
            if (delete) {
                log.info("文件删除成功：{}", filePath);
            } else {
                log.error("文件删除失败：{}", filePath);
            }
        } catch (Exception e) {
            log.error("文件删除异常：{}", filePath);
            e.printStackTrace();
        }
    }

}
