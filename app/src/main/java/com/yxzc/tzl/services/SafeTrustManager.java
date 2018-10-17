package com.yxzc.tzl.services;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.services
 * @Author: HSL
 * @Time: 2018/10/12 11:20
 * @E-mail: 13967189624@163.com
 * @Description:认证规则
 */
public class SafeTrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
