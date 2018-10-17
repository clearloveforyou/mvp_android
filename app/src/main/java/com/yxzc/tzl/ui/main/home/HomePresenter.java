package com.yxzc.tzl.ui.main.home;

import com.yxzc.tzl.base.IBasePresenterImpl;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.ui.main.home
 * @Author: HSL
 * @Time: 2018/10/15 18:23
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class HomePresenter extends IBasePresenterImpl<HomeContract.IHomeView> implements HomeContract.IHomePresenter {

    @Override
    public void requestToastMsg() {
        if (!isAttached()) return;
        getIView().testToast("在HomeFragment里面测试!!!");
    }
}
