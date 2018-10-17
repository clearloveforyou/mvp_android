package com.yxzc.tzl.services;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.services
 * @Author: HSL
 * @Time: 2018/10/12 11:22
 * @E-mail: 13967189624@163.com
 * @Description:域名匹配规则
 */
public class SafeHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        //return hostname.equals("server.jeasonlzy.com");
        return true;
    }
}
