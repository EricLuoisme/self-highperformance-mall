package com.self.highperformance.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统一响应内容
 */
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class RespResult<T> implements Serializable {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 封装响应报文
     */
    public RespResult(RespCode respCode, T data) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
        this.data = data;
    }

    /**
     * 有些操作可能没有data, e.g. 错误操作
     */
    public RespResult(RespCode respCode) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    /** 封装成功报文 */

    public static RespResult ok() {
        return new RespResult(RespCode.SUCCESS, null);
    }

    public static RespResult ok(Object data) {
        return new RespResult(RespCode.SUCCESS, data);
    }

    /** 封装错误报文 */

    public static RespResult error() {
        return new RespResult(RespCode.ERROR, null);
    }

    public static RespResult error(String msg) {
        return new RespResult(RespCode.ERROR, msg);
    }

    public static RespResult error(RespCode respCode) {
        return new RespResult(respCode);
    }

    public static RespResult diyError(Integer code, String msg) {
        return new RespResult().setCode(code).setMsg(msg);
    }

}
