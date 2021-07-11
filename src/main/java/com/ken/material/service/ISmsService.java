package com.ken.material.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ken.material.entity.SmsTemplate;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 短信服务类
 * </p>
 *
 * @author ken
 * @since 2020-05-17
 */
public interface ISmsService extends IService<SmsTemplate> {

    /**
     * 发送短信验证码
     *
     * @param templateName 短信模板名称
     * @param telephone    电话号码
     * @param cacheKey     缓存key
     */
    void sendCode(String templateName, String telephone, String cacheKey);

    /**
     * 校验短信验证码
     *
     * @param telephone 电话号码
     * @param code      验证码
     * @param cacheKey  缓存key
     * @return 校验结果
     */
    boolean checkCode(String telephone, String code, String cacheKey);

    /**
     * 发送短信通知
     *
     * @param templateName   短信模板名称
     * @param telephones     电话号码列表
     * @param templateParams 短信内容参数
     */
    void sendNotice(String templateName, List<String> telephones, Map<String, String> templateParams);
}
