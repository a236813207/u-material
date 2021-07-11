package com.ken.material.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author: api.kuaidi100.com
 * @Date: 2020-07-14 16:54
 */
public class SignUtil {

    /**
     * 快递100加密方式统一为MD5后转大写
     *
     * @param msg
     * @return
     */
    public static String sign(String msg) {
        return DigestUtils.md5Hex(msg).toUpperCase();
    }
}
