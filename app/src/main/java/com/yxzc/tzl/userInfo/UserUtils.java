package com.yxzc.tzl.userInfo;

import com.yxzc.tzl.constants.UserConstant;
import com.yxzc.tzl.utils.ObjectUtils;
import com.yxzc.tzl.utils.SPUtils;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.userInfo
 * @Author: HSL
 * @Time: 2018/10/17 14:06
 * @E-mail: 13967189624@163.com
 * @Description:用户相关的工具类
 */
public class UserUtils {

    private static final String SP_NAME = "user_sp";
    private static UserUtils mUserUtils;
    private final SPUtils mSpUtils;
    private UserCacheInfo mUserCacheInfo;

    private UserUtils() {
        mSpUtils = SPUtils.getInstance(SP_NAME);
    }

    public static UserUtils getDefault() {
        if (mUserUtils == null) {
            mUserUtils = new UserUtils();
        }
        return mUserUtils;
    }

    /**
     * 是否登陆
     *
     * @return
     */
    public boolean isLogin() {
        if (ObjectUtils.isEmpty(getToken())) {
            return false;
        }
        return true;
    }

    /**
     * 保存用户信息
     *
     * @param cacheInfo
     */
    public void saveUserInfo(UserCacheInfo cacheInfo) {
        if (ObjectUtils.isEmpty(cacheInfo)) return;
        //用户ID
        saveUserId(cacheInfo.getUserId());
        //接口Token
        saveToken(cacheInfo.getToken());
        //用户名
        saveUserName(cacheInfo.getUserName());
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserCacheInfo getUserInfo() {
        if (mUserCacheInfo == null) {
            mUserCacheInfo = new UserCacheInfo();
        }
        mUserCacheInfo.setUserId(getUserId());
        mUserCacheInfo.setToken(getToken());
        mUserCacheInfo.setUserName(getUserName());
        return null;
    }

    /**
     * 清除用户信息
     */
    public void clearUserInfo() {
        mSpUtils.clear();
    }

    /**
     * 保存用户ID
     *
     * @param userID
     */
    public void saveUserId(long userID) {
        mSpUtils.putLong(UserConstant.USER_ID, userID);
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public long getUserId() {
        return mSpUtils.getLong(UserConstant.USER_ID, 0);
    }

    /**
     * 保存TOKEN
     *
     * @param token
     */
    public void saveToken(String token) {
        if (!ObjectUtils.isEmpty(token)) {
            mSpUtils.putString(UserConstant.TOEKN, token);
        }
    }

    /**
     * 获取Token
     *
     * @return
     */
    public String getToken() {
        return mSpUtils.getString(UserConstant.TOEKN, "");
    }

    /**
     * 保存UserName
     *
     * @param userName
     */
    public void saveUserName(String userName) {
        if (!ObjectUtils.isEmpty(userName)) {
            mSpUtils.putString(UserConstant.USER_NAME, userName);
        }
    }

    /**
     * 获取userName
     *
     * @return
     */
    public String getUserName() {
        return mSpUtils.getString(UserConstant.USER_NAME, "");
    }
}
