package com.yxzc.tzl.ui.test;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.yxzc.tzl.base.IBasePresenterImpl;
import com.yxzc.tzl.beans.CalendarBean;
import com.yxzc.tzl.beans.CalendarItem;
import com.yxzc.tzl.beans.TvCategoryBean;
import com.yxzc.tzl.beans.TvCategoryItem;
import com.yxzc.tzl.services.DialogCallBack;
import com.yxzc.tzl.services.UserUrl;

import java.util.List;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.ui.test
 * @Author: HSL
 * @Time: 2018/10/16 11:44
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class TestPresenter extends IBasePresenterImpl<TestContract.ITestView>
        implements TestContract.ITestPresenter {

    /**
     * 根据日期获取万年历数据
     *
     * @param date 2013-1-3
     */
    @Override
    public void requestCalendar(String date) {
        OkGo.<CalendarBean>get(UserUrl.CALENDAR_DAY)
                .tag(getIView())
                .params("key", "a0b1f21b3dc536a1ef76dacacdeee700")
                .params("date", date)
                .execute(new DialogCallBack<CalendarBean>() {
                    @Override
                    public void onSuccess(Response<CalendarBean> response) {
                        CalendarItem data = response.body().getResult().getData();
                        getIView().requestCalendarSuccess(data);
                    }
                });
    }

    /**
     * 请求电视台分类
     */
    @Override
    public void requestTvCategory() {
        OkGo.<TvCategoryBean>get("http://japi.juhe.cn/tv/getCategory")
                .tag(getIView())
                .params("key", "af3d7acf9e9082b1f0658a1404eca05a")
                .execute(new DialogCallBack<TvCategoryBean>() {
                    @Override
                    public void onSuccess(Response<TvCategoryBean> response) {
                        List<TvCategoryItem> result = response.body().getResult();
                        getIView().requestTVSuccess(result);
                    }
                });
        Logger.t("K").e("mView：" + getIView());
    }
}
