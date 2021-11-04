package com.self.highperformance;

/**
 * 响应码
 */
public enum RespCode {

    /** 操作成功 */
    SUCCESS(20000, "Success"),

    /** 操作失败 */
    ERROR(50000, "Operation Fail"),

    /** 系统错误 */
    SYSTEM_ERROR(50001, "System Error");

    private Integer code;
    private String msg;


    RespCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
