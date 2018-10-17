package com.yxzc.tzl.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yxzc.tzl.R;
import com.yxzc.tzl.base.BaseActivity;
import com.yxzc.tzl.ui.test.TestActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author: HouShengLi
 * @Time: 2018/9/27 16:54
 * @E-mail: 13967189624@163.com
 * @Description:登陆界面
 */
public class LoginActivity extends BaseActivity {

    public static void start(Activity activity) {
        Intent starter = new Intent(activity, LoginActivity.class);
        activity.startActivity(starter);
        activity.overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    /**
     * 登陆
     */
    @OnClick(R.id.login_tv)
    public void onViewClicked() {
        TestActivity.start(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.activity_alpha_enter, R.anim.activity_alpha_exit);
    }
}
