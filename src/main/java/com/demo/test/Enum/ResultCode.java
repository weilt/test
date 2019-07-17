package com.demo.test.Enum;

public enum  ResultCode {
    SUCCESS(200, "成功!"),
    ERROR(300, "错误!"),
    NO_RECORD(301,"没有点赞记录"),
    NO_ARTICLE(302,"暂时没有文章"),
    /**
     * 系统异常编码
     */
    SYS_ERROR(100, "系统错误"),
    ;



    public int code;
    public String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static int getCode(String msg) {

        for (ResultCode resultCode : values()) {
            if (resultCode.getMsg().equals(msg))
                return resultCode.getCode();
        }
        return 0;
    }

    public static String getMsg(int code) {
        for (ResultCode resultCode : values()) {
            if (resultCode.getCode()==code) {
                return resultCode.getMsg();
            }
        }
        return null;
    }


    public static void main(String[] args) {

        System.out.println(getCode("成功!"));

    }
}
