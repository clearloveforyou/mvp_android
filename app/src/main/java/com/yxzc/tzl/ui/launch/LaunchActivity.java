package com.yxzc.tzl.ui.launch;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.Permission;
import com.yxzc.tzl.base.BaseActivity;
import com.yxzc.tzl.constants.CommonCode;
import com.yxzc.tzl.ui.main.Main;
import com.yxzc.tzl.utils.permission.PermissionUtils;

import java.lang.ref.WeakReference;

/**
 * @Author: HouShengLi
 * @Time: 2018/9/27 12:04
 * @E-mail: 13967189624@163.com
 * @Description: 应用启动页
 */
public class LaunchActivity extends BaseActivity {

    private LaunchHandler mHandler = new LaunchHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2018/9/27 可以做一些初始化工作
//        mHandler.sendEmptyMessageDelayed(CommonCode.LAUNCH_MAIN, CommonCode.LAUNCH_DELAY);
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
        mPermissionUtils.requestPermission(CommonCode.READ_EXTERNAL_STORAGE_CODE,
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private PermissionUtils mPermissionUtils = new PermissionUtils() {
        @Override
        protected void onPermissionResult(boolean isSuccess, Permission permission, int requestCode) {
            startMain();
        }
    };

    private static class LaunchHandler extends Handler {

        //弱引用<引用外部类>
        WeakReference<FragmentActivity> mActivity;

        LaunchHandler(FragmentActivity activity) {
            //构造创建弱引用
            mActivity = new WeakReference<FragmentActivity>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            //通过弱引用获取外部类.
            FragmentActivity activity = mActivity.get();
            //进行非空再操作
            if (activity != null) {
                switch (msg.what) {
                    case CommonCode.LAUNCH_MAIN:
                        // TODO: 2018/10/10
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
