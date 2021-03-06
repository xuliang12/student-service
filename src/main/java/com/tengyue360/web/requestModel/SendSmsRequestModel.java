package com.tengyue360.web.requestModel;

import com.tengyue360.enums.ValidateCodeEnum;

/**
 * 封装验证码请求参数
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

public class SendSmsRequestModel extends BaseRequestModel {


    private String phone; //手机号

    private String operateType;//获取验证码操作类型

    private ValidateCodeEnum validateType;//验证码类型



    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ValidateCodeEnum getValidateType() {
        return validateType;
    }

    public void setValidateType(ValidateCodeEnum validateType) {
        this.validateType = validateType;
    }

    @Override
    public String toString() {
        return "SendSmsRequestModel{" +
                "phone='" + phone + '\'' +
                ", operateType='" + operateType + '\'' +
                ", validateType='" + validateType + '\'' +
                '}';
    }
}
