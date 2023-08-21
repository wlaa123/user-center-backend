package com.wangan.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 */
@Data//支持get和set方法;
public class BaseResponse<T> implements Serializable {//使用泛型，增加可复用性,泛型里面必须都是类
    //支持序列化
    private int code;
    private T data;
    private String message;
    private String description;
    public BaseResponse(int code, T data, String message,String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }
    public BaseResponse(int code,T data){
        this(code,data,"","");
    }
    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }

}
