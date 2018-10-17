package com.yxzc.tzl.beans;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.beans
 * @Author: HSL
 * @Time: 2018/10/16 16:05
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class CalendarBean extends BaseBean<CalendarBean> {

    private CalendarItem data;

    public CalendarItem getData() {
        return data;
    }

    public void setData(CalendarItem data) {
        this.data = data;
    }
}
