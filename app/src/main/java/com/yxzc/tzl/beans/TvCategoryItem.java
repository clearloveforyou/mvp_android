package com.yxzc.tzl.beans;

import java.io.Serializable;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.beans
 * @Author: HSL
 * @Time: 2018/10/16 18:32
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class TvCategoryItem implements Serializable {

    /**
     * id : 2
     * name : 卫视
     */

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
