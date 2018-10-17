package com.yxzc.tzl.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yxzc.tzl.R;
import com.yxzc.tzl.base.BaseMVPActivity;
import com.yxzc.tzl.beans.FlagEvent;
import com.yxzc.tzl.constants.MainCode;
import com.yxzc.tzl.ui.main.home.HomeFragment;
import com.yxzc.tzl.ui.main.manage.ManageFragment;
import com.yxzc.tzl.ui.main.mine.MineFragment;
import com.yxzc.tzl.utils.AppUtils;
import com.yxzc.tzl.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author: HouShengLi
 * @Time: 2018/9/27 14:57
 * @E-mail: 13967189624@163.com
 * @Description: 处理固有逻辑
 */
public abstract class BaseMainActivity extends BaseMVPActivity<MainPresenter>
        implements MainContract.IMainView {

    @BindView(R.id.tab_first_iv)
    ImageView tabFirstIv;
    @BindView(R.id.tab_first_tv)
    TextView tabFirstTv;
    @BindView(R.id.main_tab_first_ll)
    LinearLayout mainTabFirstLl;
    @BindView(R.id.tab_second_iv)
    ImageView tabSecondIv;
    @BindView(R.id.tab_second_tv)
    TextView tabSecondTv;
    @BindView(R.id.main_tab_second_ll)
    LinearLayout mainTabSecondLl;
    @BindView(R.id.tab_third_iv)
    ImageView tabThirdIv;
    @BindView(R.id.tab_thidd_tv)
    TextView tabThiddTv;
    @BindView(R.id.main_tab_third_ll)
    LinearLayout mainTabThirdLl;

    private HashMap<String, Fragment> mFragments = new HashMap<String, Fragment>();
    private HomeFragment mHomeFragment;
    private ManageFragment mManageFragment;
    private MineFragment mMineFragment;
    private int mCurrentTabIndex;
    private int mDefaultShowTabIndex;
    private boolean mIsInitFragment = true;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initFragment(savedInstanceState);
    }

    /**
     * 创建Presenter
     *
     * @return
     */
    @Override
    protected MainPresenter onCreatePresenter() {
        return new MainPresenter();
    }

    /**
     * Tab1
     */
    @OnClick(R.id.main_tab_first_ll)
    public void onMainTabFirstLlClicked() {
        if (!mIsInitFragment && mCurrentTabIndex == MainCode.MAIN_TAB_FIRST) {
            // TODO: 2018/9/24 当再次点击时，做一些刷新操作

            return;
        }
        switchTabStatus(MainCode.MAIN_TAB_FIRST);
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.newInstance();
        }
        attachFragment(mHomeFragment, MainCode.MAIN_TAB_FIRST);
        showFragment(mHomeFragment);
        mCurrentTabIndex = MainCode.MAIN_TAB_FIRST;
    }

    /**
     * Tab2
     */
    @OnClick(R.id.main_tab_second_ll)
    public void onMainTabSecondLlClicked() {
        if (!mIsInitFragment && mCurrentTabIndex == MainCode.MAIN_TAB_SECOND) {
            // TODO: 2018/9/24 当再次点击时，做一些刷新操作
            return;
        }
        switchTabStatus(MainCode.MAIN_TAB_SECOND);
        if (mManageFragment == null) {
            mManageFragment = ManageFragment.newInstance();
        }
        attachFragment(mManageFragment, MainCode.MAIN_TAB_SECOND);
        showFragment(mManageFragment);
        mCurrentTabIndex = MainCode.MAIN_TAB_SECOND;
    }

    /**
     * Tab3
     */
    @OnClick(R.id.main_tab_third_ll)
    public void onMainTabThirdLlClicked() {
        if (!mIsInitFragment && mCurrentTabIndex == MainCode.MAIN_TAB_THRID) {
            // TODO: 2018/9/24 当再次点击时，做一些刷新操作
            return;
        }
        switchTabStatus(MainCode.MAIN_TAB_THRID);
        if (mMineFragment == null) {
            mMineFragment = MineFragment.newInstance();
        }
        attachFragment(mMineFragment, MainCode.MAIN_TAB_THRID);
        showFragment(mMineFragment);
        mCurrentTabIndex = MainCode.MAIN_TAB_THRID;
    }

    /**
     * EventBus消息处理
     * 显示相应的Fragment
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void mainTabEvent(FlagEvent event) {
        String key = event.getKey();
        if (key.equals(MainCode.SWITCH_MAIN_TAB)) {
            //切换TAB
            Object data = event.getData();
            if (data instanceof Integer) {
                int index = (int) data;
                switchMainTab(index);
            }
        } else {
            // TODO: 2018/10/16
        }
    }

    /**
     * 初始化Fragment
     *
     * @param savedInstanceState
     */
    private void initFragment(Bundle savedInstanceState) {
        mIsInitFragment = true;
        if (savedInstanceState == null) {
            if (mDefaultShowTabIndex == MainCode.MAIN_TAB_FIRST) {
                //默认显示TAB1
                mHomeFragment = HomeFragment.newInstance();
                mCurrentTabIndex = MainCode.MAIN_TAB_FIRST;
                onMainTabFirstLlClicked();
            } else if (mDefaultShowTabIndex == MainCode.MAIN_TAB_SECOND) {
                //默认显示TAB2
                mManageFragment = ManageFragment.newInstance();
                mCurrentTabIndex = MainCode.MAIN_TAB_SECOND;
                onMainTabSecondLlClicked();
            } else {
                //默认显示TAB3
                mMineFragment = MineFragment.newInstance();
                mCurrentTabIndex = MainCode.MAIN_TAB_THRID;
                onMainTabSecondLlClicked();
            }
        } else {
            mHomeFragment = (HomeFragment) getSupportFragmentManager()
                    .findFragmentByTag(makeTabTag(MainCode.MAIN_TAB_FIRST));
            mManageFragment = (ManageFragment) getSupportFragmentManager()
                    .findFragmentByTag(makeTabTag(MainCode.MAIN_TAB_SECOND));
            mMineFragment = (MineFragment) getSupportFragmentManager()
                    .findFragmentByTag(makeTabTag(MainCode.MAIN_TAB_THRID));
            resetAttachFragment(mHomeFragment, MainCode.MAIN_TAB_FIRST);
            resetAttachFragment(mManageFragment, MainCode.MAIN_TAB_SECOND);
            resetAttachFragment(mMineFragment, MainCode.MAIN_TAB_THRID);
            int lastTabIndex = savedInstanceState.getInt(MainCode.CURRENT_TAB_INDEX, 0);
            Fragment lastFragment = mFragments.get(makeTabTag(lastTabIndex));
            switchTabStatus(lastTabIndex);
            attachFragment(lastFragment, lastTabIndex);
            showFragment(lastFragment);
            mCurrentTabIndex = lastTabIndex;
        }
        mIsInitFragment = false;
    }

    private void resetAttachFragment(Fragment fragment, int position) {
        if (fragment != null && fragment.isAdded()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.hide(fragment);
            transaction.commitAllowingStateLoss();
            mFragments.put(makeTabTag(position), fragment);
        }
    }

    /**
     * 关联Fragment
     *
     * @param fragment
     * @param position
     */
    private void attachFragment(Fragment fragment, int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        //通过isAdded()这个方法判断Fragment是否被add可能并不准确，所以设置Tag双重判断
        //避免Fragment already added问题
        Fragment isAddingFragment = fragmentManager.findFragmentByTag(makeTabTag(position));
        if (isAddingFragment == null && fragment != null && !fragment.isAdded()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.main_container_fl, fragment, makeTabTag(position));
            transaction.commitAllowingStateLoss();
            mFragments.put(makeTabTag(position), fragment);
        }
    }

    /**
     * 显示Fragment
     *
     * @param fragment 将要显示的Fragment
     */
    private void showFragment(Fragment fragment) {
        if (fragment == null) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragments.get(makeTabTag(mCurrentTabIndex));
        if (currentFragment != null) transaction.hide(currentFragment);
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MainCode.CURRENT_TAB_INDEX, mCurrentTabIndex);
    }

    public String makeTabTag(int tabIndex) {
        return String.format("maintab_%s", tabIndex);
    }

    /**
     * 切换Tab状态：文本 ICON
     *
     * @param position
     */
    private void switchTabStatus(int position) {
        //选中处理
        int selectedColor = ContextCompat.getColor(this, R.color.color_selected_tab);
        if (position == MainCode.MAIN_TAB_FIRST) {
            tabFirstTv.setTextColor(selectedColor);
        } else if (position == MainCode.MAIN_TAB_SECOND) {
            tabSecondTv.setTextColor(selectedColor);
        } else {
            tabThiddTv.setTextColor(selectedColor);
        }
        //恢复默认状态
        if (mIsInitFragment) return;
        int defaultColor = ContextCompat.getColor(this, R.color.color_default_tab);
        if (mCurrentTabIndex == MainCode.MAIN_TAB_FIRST) {
            tabFirstTv.setTextColor(defaultColor);
        } else if (mCurrentTabIndex == MainCode.MAIN_TAB_SECOND) {
            tabSecondTv.setTextColor(defaultColor);
        } else {
            tabThiddTv.setTextColor(defaultColor);
        }
    }

    /**
     * 根据下标切换Tab
     *
     * @param index
     */
    private void switchMainTab(int index) {
        if (index == MainCode.MAIN_TAB_FIRST) {
            onMainTabFirstLlClicked();
        } else if (index == MainCode.MAIN_TAB_SECOND) {
            onMainTabSecondLlClicked();
        } else {
            onMainTabThirdLlClicked();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showShort("再按一次退出程序");
                mExitTime = System.currentTimeMillis();
            } else {
                AppUtils.exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
