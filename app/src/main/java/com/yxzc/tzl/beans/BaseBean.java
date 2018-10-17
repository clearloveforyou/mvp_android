package com.yxzc.tzl.beans;

import java.io.Serializable;

/**
 * @Author: HouShengLi
 * @Time: 2018/9/27 16:17
 * @E-mail: 13967189624@163.com
 * @Description:BaseBean必须继承
 */
public class BaseBean<T> implements Serializable {

    private int error_code;
    private T result;
    private String reason;

    @Override
    public String toString() {
        return "BaseBean{" +
                "error_code=" + error_code +
                ", result=" + result +
                ", reason='" + reason + '\'' +
                '}';
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
