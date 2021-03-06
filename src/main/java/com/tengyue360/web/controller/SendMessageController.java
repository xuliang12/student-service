package com.tengyue360.web.controller;


import com.tengyue360.enums.ValidateCodeEnum;
import com.tengyue360.service.SmsService;
import com.tengyue360.web.BeanValidators.BeanValidators;
import com.tengyue360.web.requestModel.SendSmsRequestModel;
import com.tengyue360.web.responseModel.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


/**
 * 发送短信管理类
 *
 * @author xuliang
 * @date 2018/8/11 10:03
 */

@RestController
@RequestMapping(value = "/unauth/sms")
public class SendMessageController {


    private static Logger logger = LoggerFactory.getLogger(SendMessageController.class);

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SmsService smsService;

    /**
     * 获取登录短信验证码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendLoginSms", method = RequestMethod.POST)
    public ResponseResult sendLoginSms(@RequestBody SendSmsRequestModel model) {
        logger.info("获取登录短信验证码，参数信息：{}", model);
        long starttime = System.currentTimeMillis();
        if (null != BeanValidators.isValidateLoginSms(model, redisTemplate)) {
            logger.info("获取登录短信验证码，参数验证失败：{}", BeanValidators.isValidateLoginSms(model, redisTemplate));
            return BeanValidators.isValidateLoginSms(model, redisTemplate);
        }
        //调用后台服务 获取验证码
        model.setValidateType(ValidateCodeEnum.LOGIN_CODE);
        ResponseResult responseResult = smsService.getValidateCode(model);
        if (null != responseResult) {
            logger.info("获取登录短信验证码成功吗，返回信息x：{}", responseResult);
            logger.info("调用结束，接口耗时：{}", System.currentTimeMillis() - starttime);
            return responseResult;
        }

        logger.info("调用结束，接口耗时：{}", System.currentTimeMillis() - starttime);
        return null;

    }


    /**
     * 获取修改/忘记密码短信验证码
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendUpdatePwdSms", method = RequestMethod.POST)
    public ResponseResult sendUpdatePwdSms(@RequestBody SendSmsRequestModel model) {
        logger.info("获取修改密码短信验证码，参数信息：{}", model);
        if (null != BeanValidators.isValidateForgetPwdSms(model,redisTemplate)) {
            logger.info("获取修改密码短信验证码，参数验证失败：{}", BeanValidators.isValidateForgetPwdSms(model,redisTemplate));
            return BeanValidators.isValidateForgetPwdSms(model,redisTemplate);
        }
        //调用后台服务 获取验证码
        model.setValidateType(ValidateCodeEnum.FORGET_PWD_CODE);
        ResponseResult responseResult = smsService.getValidateCode(model);
        if (null != responseResult) {
            logger.info("获取修改短信验证码成功吗，返回信息x：{}", responseResult);
            return responseResult;
        }
        return null;

    }


}
