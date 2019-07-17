package com.demo.test.common;

import com.demo.test.Enum.ResultCode;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 统一返回实体
 * @param <T>
 */
public class ResultEntity<T> {
    private int code;
    private String msg;
    private T data;

    private ResultEntity(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultEntity(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultEntity(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResultEntity(int code) {
        this.code = code;
    }

    @JsonIgnore
    public boolean isSuccess(){
        return this.code ==ResultCode.SUCCESS.getCode();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ResultEntity<T> createBySuccess(){
        return new ResultEntity<T>(ResultCode.SUCCESS.getCode());
    }

    public static <T> ResultEntity<T> createBySuccessMessage(String msg){
        return new ResultEntity<T>(ResultCode.SUCCESS.getCode(),msg);
    }

    public static <T> ResultEntity<T> createBySuccess(T data){
        return new ResultEntity<T>(ResultCode.SUCCESS.getCode(),data);
    }

    public static <T> ResultEntity<T> createBySuccess(String msg,T data){
        return new ResultEntity<T>(ResultCode.SUCCESS.getCode(),msg,data);
    }
    public static <T> ResultEntity<T> createBySuccess(int code,String msg,T data){
        return new ResultEntity<T>(code,msg,data);
    }

    public static <T> ResultEntity<T> createByError(){
        return new ResultEntity<T>(ResultCode.ERROR.getCode(),ResultCode.ERROR.getMsg());
    }

    public static <T> ResultEntity<T> createByErrorMessage(String errorMessage){
        return new ResultEntity<T>(ResultCode.ERROR.getCode(),errorMessage);
    }

    public static <T> ResultEntity<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ResultEntity<T>(errorCode,errorMessage);
    }
}
