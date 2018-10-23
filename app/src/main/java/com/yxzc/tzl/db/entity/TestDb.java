package com.yxzc.tzl.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.db.entity
 * @Author: HSL
 * @Time: 2018/10/23 14:57
 * @E-mail: 13967189624@163.com
 * @Description:
 */
@Entity
public class TestDb {

    @Id(autoincrement = true)
    private Long id;

    private String testName;

    private int testNo;

    private boolean sex;

    @Generated(hash = 1200392979)
    public TestDb(Long id, String testName, int testNo, boolean sex) {
        this.id = id;
        this.testName = testName;
        this.testNo = testNo;
        this.sex = sex;
    }

    @Generated(hash = 947626369)
    public TestDb() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestName() {
        return this.testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getTestNo() {
        return this.testNo;
    }

    public void setTestNo(int testNo) {
        this.testNo = testNo;
    }

    public boolean getSex() {
        return this.sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

}
