package com.ken.material.config;

import com.aliyun.oss.OSSClient;
import com.ken.material.utils.oss.OSSClientFactory;
import com.ken.material.utils.oss.OSSManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ken
 * @date 2020/3/28
 * @description
 */
@Configuration
public class OssConfig {

    @Bean
    public OSSManager createOssManager(OSSClient ossClient, @Value("${aliyun.oss.bucket}") String bucketsName,
                                       @Value("${aliyun.oss.endpoint}") String aliyunEndpoint) {
        Map<String,String> buckets = new HashMap<>(1);
        buckets.put(bucketsName,"http://"+bucketsName+"."+aliyunEndpoint);
        return new OSSManager(ossClient, buckets);
    }

    @Bean
    public OSSClient createOssClient(@Value("${aliyun.oss.accessKeyId}") String keyId,
                                     @Value("${aliyun.oss.endpoint}") String aliyunEndpoint,
                                     @Value("${aliyun.oss.accessKeySecret}") String keySecret) {
        return OSSClientFactory.getOSSClient(aliyunEndpoint, keyId, keySecret);
    }
}
