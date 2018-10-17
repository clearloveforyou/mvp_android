package com.yxzc.tzl.userInfo;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.userInfo
 * @Author: HSL
 * @Time: 2018/10/17 13:52
 * @E-mail: 13967189624@163.com
 * @Description: 用户缓存信息：和用户相关在此处添加
 */
public class UserCacheInfo {

    /**
     * 用户id
     */
    private long userId = 0;
    /**
     * 接口token
     */
    private String token = "";
    /**
     * 用户名(手机号、账号)
     */
    private String userName = "";


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
