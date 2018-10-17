package com.yxzc.tzl.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.lzy.okgo.OkGo;
import com.orhanobut.logger.Logger;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.base
 * @Author: HSL
 * @Time: 2018/10/15 17:20
 * @E-mail: 13967189624@163.com
 * @Description: 纯粹的 MVP 包装，不要增加任何View层基础功能
 * 如果要添加基类功能，请在{@link BaseActivity} 中添加
 */
public abstract class BaseMVPActivity<P extends IBasePresenter> extends BaseActivity implements IBaseView {

    protected P mPresenter;

    protected FragmentActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mPresenter = onCreatePresenter();
        if (mPresenter == null) {
            throw new NullPointerException("Presenter is null! Do you return null in createPresenter()?");
        }
        mPresenter.onAttachView(this);
        //固定的流程
        //固定的流程
        //固定的流程
        initExtraData();
        initView();
        initData();
        initListener();
    }

    /**
     * 抽象方法
     * 创建Presenter
     *
     * @return
     */
    protected abstract P onCreatePresenter();

    /**
     * 初始化Bundle携带的数据
     */
    protected void initExtraData() {

    }

    /**
     * 初始化View
     */
    protected void initView() {
        // TODO: 2018/10/15 根据需要重写
    }

    /**
     * 初始化数据
     */
    protected void initData() {
        // TODO: 2018/10/15 根据需要重写
    }

    /**
     * 设置监听事件
     */
    protected void initListener() {
        // TODO: 2018/10/15 根据需要重写
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetachView();
        OkGo.getInstance().cancelTag(this);
        Logger.t("K").e("当前对象：" + this);
    }
}
