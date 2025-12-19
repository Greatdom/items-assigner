package com.wddyxd.common.utils;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.wddyxd.common.constant.CommonConstant;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @program: items-assigner
 * @description: description
 * @author: wddyxd
 * @create: 2025-12-19 22:56
 **/

public class FileOperateUtil {

    // 文件上传
    public Result<String> uploadFile(MultipartFile file) {
        //TODO 高并发可能导致时间戳重复
        String flag = System.currentTimeMillis() + "";
        String fileName = file.getOriginalFilename();

        try {
            if (!FileUtil.isDirectory(CommonConstant.filePath)) {
                FileUtil.mkdir(CommonConstant.filePath);
            }
            // 文件存储形式：时间戳-文件名
            FileUtil.writeBytes(file.getBytes(), CommonConstant.filePath + flag + "-" + fileName);  // ***/manager/files/1697438073596-avatar.png
            System.out.println(fileName + "--上传成功");

        } catch (Exception e) {
            System.err.println(fileName + "--文件上传失败");
        }
        // 使用配置的域名生成文件 URL
        return Result.success(CommonConstant.serverDomain + "/files/" + flag + "-" + fileName);
    }

    // 获取文件
    //  1697438073596-avatar.png
    public void getFilePath(String flag, HttpServletResponse response) {
        OutputStream os;
        try {
            if (StrUtil.isNotEmpty(flag)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(flag, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(CommonConstant.filePath + flag);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }

    // 删除文件
    public void delFile(String flag) {
        FileUtil.del(CommonConstant.filePath + flag);
        System.out.println("删除文件" + flag + "成功");
    }

}
