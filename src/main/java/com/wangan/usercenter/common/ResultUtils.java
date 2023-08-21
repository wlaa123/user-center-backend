package com.wangan.usercenter.common;

/**
 * 返回工具类
 */
public class ResultUtils {
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T>BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok","");
    }

    /**
     * 失败
     * @param errorcode
     * @return
     */
    public static BaseResponse error(ErrorCode errorcode){
//        return new BaseResponse<>(errorcode.getCode(),null,errorcode.getMessage(),errorcode.getDescription());
        return new BaseResponse<>(errorcode);
    }
    public static BaseResponse error(int code,String message,String description){
        return new BaseResponse<>(code,null,message,description);
    }
    public static BaseResponse error(ErrorCode errorCode,String message,String description){
        return new BaseResponse<>(errorCode.getCode(),null,message,description);
    }
    public static BaseResponse error(ErrorCode errorCode,String description){
        return new BaseResponse<>(errorCode.getCode(),description);
    }
}
