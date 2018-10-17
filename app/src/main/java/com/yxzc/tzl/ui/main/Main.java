package com.yxzc.tzl.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.yxzc.tzl.utils.ToastUtils;

/**
 * @Project: tzl_android
 * @Package: com.yxzc.tzl.ui.activity
 * @Author: HSL
 * @Time: 2018/09/27 14:57
 * @E-mail: 13967189624@163.com
 * @Description:做一些预加载操作
 */
public class Main extends BaseMainActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, Main.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("hello");
        // TODO: 2018/10/15 测试
        mPresenter.requestToastMsg();
    }

    /**
     * 测试
     *
     * @param msg
     */
    @Override
    public void testToast(String msg) {
        ToastUtils.showShort(msg);
    }
}
