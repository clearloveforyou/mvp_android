package com.yxzc.tzl.ui.main;

import com.yxzc.tzl.base.IBasePresenterImpl;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.ui.main
 * @Author: HSL
 * @Time: 2018/10/15 17:36
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class MainPresenter extends IBasePresenterImpl<MainContract.IMainView> implements MainContract.IMainPresenter {

    /**
     * 请求Toast提示
     */
    @Override
    public void requestToastMsg() {
        if (!isAttached()) return;
        getIView().testToast("测试Main!!!");
    }
}
