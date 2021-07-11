package com.ken.material.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.javaer.aliyun.sms.SmsClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.ken.material.common.code.BizCodeFace;
import com.ken.material.common.code.ErrorCode;
import com.ken.material.common.constant.RegexPatterns;
import com.ken.material.common.exception.BizException;
import com.ken.material.constant.CommonCacheKey;
import com.ken.material.entity.SmsTemplate;
import com.ken.material.enums.Status;
import com.ken.material.mapper.SmsTemplateMapper;
import com.ken.material.service.ISmsService;
import com.ken.material.utils.EHCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ken
 * @version 1.0
 * @date 2020-09-15
 */
@Service
public class SmsServiceImpl extends ServiceImpl<SmsTemplateMapper, SmsTemplate> implements ISmsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ISmsService.class);
    /**
     * 系统默认短信验证码
     */
    private final static String SMC_CODE = "123456";

    @Value("${sms.code.realSend:true}")
    private boolean sendRealSmsCode;

    private SmsClient smsClient;

    @Override
    public void sendCode(String templateName, String telephone, String cacheKey) {
        if (!telephone.matches(RegexPatterns.MOBILE_PHONE_NUMBER_PATTERN)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.FAIL).message("手机号码不正确"));
        }

        String code = SMC_CODE;
        if (sendRealSmsCode) {
            SmsTemplate smsTemplate = getSmsTemplate(templateName);

            if (smsTemplate == null) {
                throw new BizException(BizCodeFace.createBizCode(ErrorCode.DATE_NULL).message("未配置短信模板"));
            }
            code = RandomUtil.randomNumbers(4);

            cn.javaer.aliyun.sms.SmsTemplate noticeSmsTemplate = cn.javaer.aliyun.sms.SmsTemplate.builder().signName(smsTemplate.getSign())
                    .phoneNumbers(Lists.newArrayList(telephone))
                    .templateCode(smsTemplate.getTemplateCode())
                    .addTemplateParam("code", code)
                    .build();
            this.smsClient.send(noticeSmsTemplate);
        }
        EHCacheUtils.setCache(CommonCacheKey.CACHE_NAME_SMS_CODE, cacheKey, code);
        LOGGER.debug("成功给[{}]发送了模板名称为[{}]的短信验证码[{}]", telephone, templateName, code);
    }

    @Override
    public boolean checkCode(String telephone, String code, String cacheKey) {
        if (!telephone.matches(RegexPatterns.MOBILE_PHONE_NUMBER_PATTERN)) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.FAIL).message("手机号码不正确"));
        }
        Object cacheValue = EHCacheUtils.getCache(CommonCacheKey.CACHE_NAME_SMS_CODE, cacheKey);
        boolean result = false;
        if (cacheValue != null) {
            result = code.equals(String.valueOf(cacheValue));
        }

        //校验通过后删除验证码缓存
        if (result) {
            EHCacheUtils.removeCache(CommonCacheKey.CACHE_NAME_SMS_CODE, cacheKey);
        }
        return result;
    }

    @Override
    public void sendNotice(String templateName, List<String> telephones, Map<String, String> templateParams) {
        SmsTemplate smsTemplate = getSmsTemplate(templateName);
        if (smsTemplate == null) {
            throw new BizException(BizCodeFace.createBizCode(ErrorCode.DATE_NULL).message("未配置短信模板"));
        }

        cn.javaer.aliyun.sms.SmsTemplate noticeSmsTemplate = cn.javaer.aliyun.sms.SmsTemplate.builder().signName(smsTemplate.getSign())
                .phoneNumbers(telephones)
                .templateCode(smsTemplate.getTemplateCode())
                .templateParam(templateParams)
                .build();
        this.smsClient.send(noticeSmsTemplate);
        LOGGER.debug("成功给[{}]发送了短信模板名称为[{}]的短信,变量参数为[{}]", telephones, templateName, templateParams);
    }

    /**
     * 查询短信模板
     *
     * @param templateName 短信模板名称
     * @return SmsTemplate
     */
    private SmsTemplate getSmsTemplate(String templateName) {
        QueryWrapper<SmsTemplate> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SmsTemplate::getTemplateName, templateName).last("limit 1");
        SmsTemplate smsTemplate = this.getOne(wrapper);

        if (smsTemplate == null) {
            LOGGER.error("短信模板[{}]未配置", templateName);
            return null;
        }

        if (Status.enable.equals(smsTemplate.getStatus())) {
            return smsTemplate;
        }
        LOGGER.warn("短信模板[{}]未启用", templateName);
        return null;
    }

    @Autowired
    public void setSmsClient(SmsClient smsClient) {
        this.smsClient = smsClient;
    }
}
