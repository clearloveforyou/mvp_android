package com.yxzc.tzl.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxzc.tzl.R;

/**
 * @Author: HouShengLi
 * @Time: 2018/10/11 14:10
 * @E-mail: 13967189624@163.com
 * @Description:TitleBar
 */
public class HeadBar extends LinearLayout {

    private Context mContext;
    private RelativeLayout relBack;
    private TextView centerTitle;
    private TextView rightTitle;
    private ImageView rightImage;
    private String cTitle;
    private int cColor;
    private float cSize;
    private String rTitle;
    private int rColor;
    private float rSize;

    public HeadBar(Context context) {
        this(context, null);
    }

    public HeadBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        obtainAttributes(context, attrs);
        setOrientation(VERTICAL);
        inflate(context, R.layout.head_bar, this);
        relBack = findViewById(R.id.bar_back_rel);
        centerTitle = findViewById(R.id.bar_center_title);
        rightTitle = findViewById(R.id.bar_right_title);
        rightImage = findViewById(R.id.bar_right_image);
        if (cTitle != null) {
            centerTitle.setText(cTitle);
        }
        centerTitle.setTextColor(cColor);
        centerTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, cSize);
        if (rTitle != null) {
            rightTitle.setText(rTitle);
        }
        rightTitle.setTextColor(rColor);
        rightTitle.setTextSize(rSize);
        listener();
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HeadBar);
        cTitle = ta.getString(R.styleable.HeadBar_centerTitle);
        cColor = ta.getColor(R.styleable.HeadBar_centerTitle_color, Color.WHITE);
        cSize = ta.getDimension(R.styleable.HeadBar_centerTitle_size, 18);
        rTitle = ta.getString(R.styleable.HeadBar_rightTitle);
        rColor = ta.getColor(R.styleable.HeadBar_rightTitle_color, Color.WHITE);
        rSize = ta.getDimension(R.styleable.HeadBar_rightTitle_size, 14);
        ta.recycle();
    }

    private void listener() {
        //返回
        try {
            if (relBack != null) {
                relBack.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Activity activity = (Activity) mContext;
                        activity.finish();
                    }
                });
            }
        } catch (Exception e) {
            // TODO: 2017/10/30 0030
        }
    }

    /**
     * 设置TITLE
     *
     * @param title
     */
    public void setCenterTitle(String title) {
        if (centerTitle != null && title != null)
            centerTitle.setText(title);
    }

    /**
     * 设置标题颜色
     *
     * @param color
     */
    public void setCenterTitleColor(int color) {
        if (centerTitle != null)
            centerTitle.setTextColor(color);

    }

    /**
     * 设置右边副标题
     *
     * @param title
     */
    public void setRightTitle(String title) {
        if (rightTitle != null && title != null)
            rightTitle.setText(title);
    }

    /**
     * 设置右边副标题
     *
     * @param title
     * @param rightTitleClick
     */
    public void setRightTitle(String title, final OnRightTitleClickListener rightTitleClick) {
        if (rightTitle != null && title != null) {
            rightTitle.setText(title);
            rightTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rightTitleClick != null) {
                        rightTitleClick.rightTitleClick();
                    }
                }
            });
        }

    }

    /**
     * 设置副标题颜色
     *
     * @param color
     */
    public void setRightTitleColor(int color) {
        if (rightTitle != null)
            rightTitle.setTextColor(color);
    }

    /**
     * 设置右边图片
     *
     * @param resId
     */
    public void setRightImage(int resId) {
        if (rightImage != null)
            rightImage.setImageResource(resId);
    }

    /**
     * 设置右边图片
     *
     * @param resId
     * @param rightImageClickListener
     */
    public void setRightImage(int resId, final OnRightImageClickListener rightImageClickListener) {
        if (rightImage != null) {
            rightImage.setImageResource(resId);
            rightImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rightImageClickListener != null)
                        rightImageClickListener.rightImageClick();
                }
            });
        }
    }


    public RelativeLayout getRelBack() {
        return relBack;
    }

    /**
     * 获取标题
     *
     * @return
     */
    public TextView getCenterTitle() {
        return centerTitle;
    }

    /**
     * 获取副标题
     *
     * @return
     */
    public TextView getRightTitle() {
        return rightTitle;
    }

    /**
     * 获取图片
     *
     * @return
     */
    public ImageView getRightImage() {
        return rightImage;
    }

    /**
     * 隐藏返回键
     */
    public void setGoneBack() {
        if (relBack != null) {
            relBack.setVisibility(GONE);
        }
    }

    /**
     * 接口
     */
    public interface OnRightTitleClickListener {
        void rightTitleClick();
    }


    public interface OnRightImageClickListener {
        void rightImageClick();
    }

}
