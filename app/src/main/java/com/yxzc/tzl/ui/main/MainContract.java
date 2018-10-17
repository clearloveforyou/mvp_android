package com.yxzc.tzl.ui.main;

import com.yxzc.tzl.base.IBasePresenter;
import com.yxzc.tzl.base.IBaseView;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.ui.main
 * @Author: HSL
 * @Time: 2018/10/15 17:30
 * @E-mail: 13967189624@163.com
 * @Description:mian契约类
 */
public interface MainContract {

    interface IMainView extends IBaseView {
        // TODO: 2018/10/15
        void testToast(String msg);
    }

    interface IMainPresenter extends IBasePresenter<IMainView> {
        // TODO: 2018/10/15
        void requestToastMsg();
    }
}
