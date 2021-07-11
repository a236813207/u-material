package com.ken.material.controller;

import com.ken.material.common.response.ResBody;
import com.ken.material.utils.oss.OSSManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * @author ken
 * @version 1.0
 * @date 2021-01-23
 */
@RequestMapping("/common")
@RestController
@Slf4j
public class CommonController {

    @Autowired
    private OSSManager ossManager;

    private @Value("${aliyun.oss.bucket}")
    String bucketsName;

    @PostMapping("/upload")
    public ResBody<?> uploadImage(@RequestParam("file") MultipartFile file, String dir) throws IOException, NoSuchAlgorithmException {
        String url = ossManager.putObject(dir, bucketsName, file);
        return ResBody.success(url);
    }

}
