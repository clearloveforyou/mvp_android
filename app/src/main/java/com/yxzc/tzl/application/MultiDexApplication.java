package com.yxzc.tzl.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * @Author: HouShengLi
 * @Time: 2018/9/27 11:23
 * @E-mail: 13967189624@163.com
 * @Description:进行分包解决“65535方法数超标”
 */
public class MultiDexApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
