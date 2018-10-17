package com.yxzc.tzl.ui.main.home;

import com.yxzc.tzl.base.IBasePresenter;
import com.yxzc.tzl.base.IBaseView;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.ui.main.home
 * @Author: HSL
 * @Time: 2018/10/15 18:23
 * @E-mail: 13967189624@163.com
 * @Description:首页
 */
public class HomeContract {

    interface IHomeView extends IBaseView {
        // TODO: 2018/10/15
        void testToast(String msg);
    }

    interface IHomePresenter extends IBasePresenter<IHomeView> {
        // TODO: 2018/10/15
        void requestToastMsg();
    }
}
