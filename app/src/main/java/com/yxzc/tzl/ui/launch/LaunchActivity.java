package com.yxzc.tzl.ui.launch;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;

import com.tbruyelle.rxpermissions2.Permission;
import com.yxzc.tzl.base.BaseActivity;
import com.yxzc.tzl.constants.PermissionCode;
import com.yxzc.tzl.ui.main.Main;
import com.yxzc.tzl.utils.permission.PermissionUtils;

/**
 * @Author: HouShengLi
 * @Time: 2018/9/27 12:04
 * @E-mail: 13967189624@163.com
 * @Description: 应用启动页
 */
public class LaunchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2018/9/27 可以做一些初始化工作
        requestPermission();
    }

    /**
     * 禁用沉浸色
     *
     * @return
     */
    @Override
    protected boolean enableImmersionBar() {
        return false;
    }

    /**
     * 启动Main
     */
    private void startMain() {
        Main.start(this);
        finish();
    }

    /**
     * 请求权限
     */
    private void requestPermission() {
        mPermissionUtils.initRxPermission(this);
        mPermissionUtils.requestPermission(PermissionCode.READ_EXTERNAL_STORAGE_CODE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private PermissionUtils mPermissionUtils = new PermissionUtils() {
        @Override
        protected void onPermissionResult(boolean isSuccess, Permission permission, int requestCode) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startMain();
                }
            }, 1000);
        }
    };
}
