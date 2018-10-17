package com.yxzc.tzl.ui.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.yxzc.tzl.R;
import com.yxzc.tzl.base.BaseMVPActivity;
import com.yxzc.tzl.beans.CalendarItem;
import com.yxzc.tzl.beans.FlagEvent;
import com.yxzc.tzl.beans.TvCategoryItem;
import com.yxzc.tzl.constants.MainCode;
import com.yxzc.tzl.ui.main.Main;
import com.yxzc.tzl.utils.ActivityUtils;
import com.yxzc.tzl.utils.ObjectUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseMVPActivity<TestPresenter> implements TestContract.ITestView {

    public static void start(Context context) {
        Intent starter = new Intent(context, TestActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @Override
    protected TestPresenter onCreatePresenter() {
        return new TestPresenter();
    }

    @Override
    protected void initExtraData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    /**
     * 测试
     * 1.结束到某个activity
     * 2.eventBus
     * 3.主页面的切换
     */
    @OnClick(R.id.test_event_tv)
    public void onTestEventClicked() {
        Activity main = ActivityUtils.getTargetActivity(Main.class);
        if (!ObjectUtils.isEmpty(main)) {
            ActivityUtils.finishToActivity(main, false);
        }
        FlagEvent<Integer> event = new FlagEvent<>();
        event.setKey(MainCode.SWITCH_MAIN_TAB);
        event.setPageClass(getClass());
        event.setData(2);
        EventBus.getDefault().post(event);
    }

    /**
     * 网络测试
     */
    @OnClick(R.id.test_http_tv)
    public void onTestHttpClicked() {
//        //请求日历
//        mPresenter.requestCalendar("2018-3-10");
        //电视台分类
        mPresenter.requestTvCategory();
    }

    /**
     * 日历请求成功
     *
     * @param calendarItem
     */
    @Override
    public void requestCalendarSuccess(CalendarItem calendarItem) {
        Logger.t("CA").e("错误：==%s", calendarItem);
    }

    /**
     * 电视台分类
     *
     * @param items
     */
    @Override
    public void requestTVSuccess(List<TvCategoryItem> items) {
        Logger.t("CA").e("错误：==%s", items);
    }

}
