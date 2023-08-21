package com.wangan.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data//自动生成get,set方法;
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;
}
