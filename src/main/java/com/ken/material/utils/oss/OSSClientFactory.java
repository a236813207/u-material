package com.ken.material.utils.oss;

import com.aliyun.oss.OSSClient;

/**
 * @author Ken
 * @date 2020/3/28
 * @description
 */
public class OSSClientFactory {

    private  OSSClientFactory(){ }

    public static final String ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";

    public static OSSClient getOSSClient(String endpoint,String accessKeyId, String accessKeySecret){
            return new OSSClient(endpoint,accessKeyId,accessKeySecret);
    }

    public static OSSClient getOSSClient(String accessKeyId, String accessKeySecret){
        return getOSSClient(ENDPOINT,accessKeyId,accessKeySecret);
    }
}
