package com.yxzc.tzl.ui.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yxzc.tzl.R;
import com.yxzc.tzl.base.BaseMVPActivity;
import com.yxzc.tzl.beans.CalendarItem;
import com.yxzc.tzl.beans.FlagEvent;
import com.yxzc.tzl.beans.TvCategoryItem;
import com.yxzc.tzl.constants.MainCode;
import com.yxzc.tzl.db.manager.TestManager;
import com.yxzc.tzl.ui.main.Main;
import com.yxzc.tzl.utils.ActivityUtils;
import com.yxzc.tzl.utils.GlideUtils;
import com.yxzc.tzl.utils.ObjectUtils;
import com.yxzc.tzl.widgets.ImageSelectDialog;
import com.yxzc.tzl.widgets.LoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseMVPActivity<TestPresenter> implements TestContract.ITestView {

    @BindView(R.id.test_event_tv)
    TextView testEventTv;
    @BindView(R.id.test_http_tv)
    TextView testHttpTv;
    @BindView(R.id.test_dialog_tv)
    TextView testDialogTv;
    @BindView(R.id.test_portrait_tv)
    TextView testPortraitTv;
    @BindView(R.id.head_portrait_riv)
    RoundedImageView headPortraitRiv;
    @BindView(R.id.refresh_test)
    SmartRefreshLayout refreshTest;
    @BindView(R.id.test_db_tv)
    TextView testDbTv;

    private ImageSelectDialog mImageSelectDialog;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mImageSelectDialog.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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
     * 测试dialog
     */
    @OnClick(R.id.test_dialog_tv)
    public void onTestDialogClicked() {
        testDialog();
    }

    /**
     * 头像处理
     */
    @OnClick(R.id.test_portrait_tv)
    public void onTestPortraitClicked() {
        if (mImageSelectDialog == null) {
            mImageSelectDialog = new ImageSelectDialog(mActivity) {
                @Override
                protected void onSelectImageResult(boolean isSuccess, Uri uri) {
                    GlideUtils.bindHeadPortrait(mActivity, uri, headPortraitRiv);
                }
            };
        }
        mImageSelectDialog.showDialog();
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

    /**
     * 测试Dialog
     */
    private void testDialog() {
        // TODO: 2018/10/8 随时可以删除
        LoadingDialog loadingDialog = new LoadingDialog(mActivity);
//        LoadingDialog loadingDialog = new LoadingDialog(getActivity(),0.6f,Gravity.BOTTOM);
        loadingDialog.showDialog();
    }

    /**
     * 测试数据库
     */
    @OnClick(R.id.test_db_tv)
    public void onTestDBClicked() {
        TestManager.saveTest("测试");
    }
}
