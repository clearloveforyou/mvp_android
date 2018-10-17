package com.yxzc.tzl.beans;

import android.os.Bundle;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.beans
 * @Author: HSL
 * @Time: 2018/09/27 17:21
 * @E-mail: 13967189624@163.com
 * @Description: EventBus
 */
public class FlagEvent<T> {
    /**
     * 页面Class
     */
    private Class<?> pageClass = null;
    /**
     * 数据包
     */
    private Bundle bundle = null;
    /**
     * 数据
     */
    private T data = null;
    /**
     * 键:用于区分功能类型，比如刷新、切换Tab
     */
    private String key = "";

    public Class<?> getPageClass() {
        return pageClass;
    }

    public void setPageClass(Class<?> pageClass) {
        this.pageClass = pageClass;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
