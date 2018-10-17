package com.yxzc.tzl.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.yxzc.tzl.utils.ActivityUtils;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.base
 * @Author: HSL
 * @Time: 2018/09/27 12:02
 * @E-mail: 13967189624@163.com
 * @Description: Activity基类
 */
public class BaseActivity extends FragmentActivity {

    private boolean mActivityIsForeground = true;
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化沉浸式
        if (enableImmersionBar())
            initImmersionBar();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Activity前后台状态
        if (!mActivityIsForeground) {
            mActivityIsForeground = true;
            onRunningForeground(true);
        }
    }

    /**
     * 初始化沉浸色
     */
    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 是否可以使用沉浸式
     *
     * @return the boolean
     */
    protected boolean enableImmersionBar() {
        return true;
    }

    /**
     * 是否运行在前台
     *
     * @param isForeground
     */
    protected void onRunningForeground(boolean isForeground) {
        String simpleName = getClass().getSimpleName();
        if (isForeground) {
            // 进入前台
            Logger.t(simpleName).d(simpleName + "进入前台");
        } else {
            // 进入后台
            Logger.t(simpleName).d(simpleName + "进入后台");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Activity前后台状态
        mActivityIsForeground = ActivityUtils.isActivityRunningForeground(this);
        if (!mActivityIsForeground) {
            mActivityIsForeground = false;
            onRunningForeground(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁沉浸色
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }
}
