package com.tengyue360.web.requestModel;

/**
 * 封装用户请求类
 *
 * @author xuliang
 * @date 2018/8/10 12:37
 */

public class UserRequestModel extends BaseRequestModel {


    private String oldPwd;//旧密码
    private String newPwd;//新密码
    private String messageCode;//验证码
    private String userId;//用户ID


    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
