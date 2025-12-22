package com.wddyxd.fileservice.rabbitMQ;


import cn.hutool.core.io.FileUtil;
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

//    @RabbitListener(queues = "${rabbitmq.queue.delete:file-delete-queue}")
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
                System.out.println("文件删除成功：" + filePath);
            } else {
                System.err.println("文件删除失败（文件不存在）：" + filePath);
            }
        } catch (Exception e) {
            System.err.println("文件删除异常：" + filePath + "，异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

}
