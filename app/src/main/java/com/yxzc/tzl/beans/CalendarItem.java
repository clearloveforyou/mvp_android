package com.yxzc.tzl.beans;

import com.google.gson.annotations.SerializedName;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.beans
 * @Author: HSL
 * @Time: 2018/10/16 16:06
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class CalendarItem {

    /**
     * avoid : 开生坟.破土.行丧.安葬.
     * animalsYear : 龙
     * weekday : 星期四
     * suit : 嫁娶.冠笄.祭祀.祈福.求嗣.斋醮.进人口.会亲友.伐木.作梁.开柱眼.安床.掘井.捕捉.畋猎.
     * lunarYear : 壬辰年
     * lunar : 十一月廿二
     * year-month : 2013-1
     * date : 2013-1-3
     */

    private String avoid;
    private String animalsYear;
    private String weekday;
    private String suit;
    private String lunarYear;
    private String lunar;
    @SerializedName("year-month")
    private String yearmonth;
    private String date;

    public String getAvoid() {
        return avoid;
    }

    public void setAvoid(String avoid) {
        this.avoid = avoid;
    }

    public String getAnimalsYear() {
        return animalsYear;
    }

    public void setAnimalsYear(String animalsYear) {
        this.animalsYear = animalsYear;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getLunarYear() {
        return lunarYear;
    }

    public void setLunarYear(String lunarYear) {
        this.lunarYear = lunarYear;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getYearmonth() {
        return yearmonth;
    }

    public void setYearmonth(String yearmonth) {
        this.yearmonth = yearmonth;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
