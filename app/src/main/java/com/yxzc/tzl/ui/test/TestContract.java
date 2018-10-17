package com.yxzc.tzl.ui.test;

import com.yxzc.tzl.base.IBasePresenter;
import com.yxzc.tzl.base.IBaseView;
import com.yxzc.tzl.beans.CalendarItem;
import com.yxzc.tzl.beans.TvCategoryItem;

import java.util.List;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.ui.test
 * @Author: HSL
 * @Time: 2018/10/16 11:44
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class TestContract {

    interface ITestView extends IBaseView {

        void requestCalendarSuccess(CalendarItem calendarItem);

        void requestTVSuccess(List<TvCategoryItem> items);
    }

    interface ITestPresenter extends IBasePresenter<ITestView> {

        void requestCalendar(String date);

        void requestTvCategory();

    }
}
