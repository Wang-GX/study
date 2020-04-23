package com.wgx.study.project.文件上传;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(value = "文件上传",tags = "文件上传")
@RestController
public class SpringMvcFileUploadController {

    @Value("${spring.servlet.multipart.location}")
    String uploadFileDirectory;//文件上传目录

    /**
     * 文件批量上传
     *
     * @param files
     * @return
     */
    @ApiOperation("文件批量上传")
    @PostMapping("/file/upload")
    public List<String> fileUpload(MultipartFile[] files) {

        List<String> filesPath = new ArrayList<>();

        //判断指定的文件上传目录是否存在，如果不存在就创建
        File directory = new File(uploadFileDirectory);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                throw new RuntimeException("目录创建失败");
            }
        }

        //批量上传
        for (MultipartFile file : files) {

            //获取当前时间
            DateTime dateTime = new DateTime();
            String dateString = dateTime.toString("(yyyy年MM月dd日HH时mm分ss秒)");

            //获取文件名
            String originalFilename = dateString + file.getOriginalFilename();
            //创建文件对象
            File filePath = new File(originalFilename);

            try {
                //保存文件
                file.transferTo(filePath);
                filesPath.add(uploadFileDirectory + originalFilename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return filesPath;
    }
}