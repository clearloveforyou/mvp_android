package com.yxzc.tzl.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.lzy.okgo.OkGo;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.base
 * @Author: HSL
 * @Time: 2018/10/15 17:20
 * @E-mail: 13967189624@163.com
 * @Description: 纯粹的 MVP 包装，不要增加任何View层基础功能
 * 如果要添加基类功能，请在{@link BaseActivity} 中添加
 */
public abstract class BaseMVPFragment<P extends IBasePresenter> extends BaseFragment implements IBaseView {

    protected P mPresenter;

    private Bundle mBundle;

    private FragmentActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (FragmentActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBundle = getArguments();
        } else {
            mBundle = new Bundle();
        }
        initExtraData(mBundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = onCreatePresenter();
        if (mPresenter == null) {
            throw new NullPointerException("Presenter is null! Do you return null in createPresenter()?");
        }
        mPresenter.onAttachView(this);
        //固定的流程
        //固定的流程
        //固定的流程
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
     *
     * @param bundle
     */
    protected void initExtraData(Bundle bundle) {

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
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDetachView();
        OkGo.getInstance().cancelTag(this);
    }

}
