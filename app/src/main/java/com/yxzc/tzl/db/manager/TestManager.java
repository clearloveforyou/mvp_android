package com.yxzc.tzl.db.manager;

import com.yxzc.tzl.application.AppContext;
import com.yxzc.tzl.dao.TestDbDao;
import com.yxzc.tzl.db.DbManager;
import com.yxzc.tzl.db.entity.TestDb;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.db.manager
 * @Author: HSL
 * @Time: 2018/10/23 15:34
 * @E-mail: 13967189624@163.com
 * @Description:
 */
public class TestManager {

    private static final TestDbDao TEST_DB_DAO;

    static {
        TEST_DB_DAO = DbManager.getDaoSession(AppContext.getInstance()).getTestDbDao();
    }

    /**
     * 删除数据
     */
    public static void deleteData(String name) {
        TestDb entity = queryTest(name);
        if (entity != null) {
            TEST_DB_DAO.delete(entity);
        }
    }

    /**
     * 删除所有
     */
    public static void deleteAllData() {
        TEST_DB_DAO.deleteAll();
    }

    /**
     * 根据条件查询绑定转态
     *
     * @return
     */
    public static TestDb queryTest(String name) {
        TestDb entity = null;
        QueryBuilder<TestDb> queryBuilder = TEST_DB_DAO.queryBuilder();
        List<TestDb> entities = queryBuilder
                .where(TestDbDao.Properties.TestName.eq(name))
                .list();
        if (!entities.isEmpty()) {
            entity = entities.get(0);
        }
        return entity;
    }

    /**
     * 添加数据至数据库
     * 如果存在，将原来的数据覆盖
     *
     * @param testName
     */
    public static void saveTest(String testName) {
        TestDb entity = queryTest(testName);
        if (entity == null) {
            TestDb testDb = new TestDb();
            entity.setTestName(testName);
            TEST_DB_DAO.insert(testDb);
        } else {
            entity.setTestName(testName);
            TEST_DB_DAO.update(entity);
        }
    }
}
