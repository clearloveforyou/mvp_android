package com.yxzc.tzl.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.yuweiguocn.library.greendao.MigrationHelper;
import com.yxzc.tzl.dao.DaoMaster;
import com.yxzc.tzl.dao.TestDbDao;

import org.greenrobot.greendao.database.Database;

/**
 * @Project: mvp_android
 * @Package: com.yxzc.tzl.db
 * @Author: HSL
 * @Time: 2018/10/23 15:13
 * @E-mail: 13967189624@163.com
 * @Description:添加任意一张表，均在此处添加其dao的类类型，以确保正常升级
 */
public class TLSqliteOpenHelper extends DaoMaster.OpenHelper {

    public TLSqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

                    @Override
                    public void onCreateAllTables(Database db, boolean ifNotExists) {
                        DaoMaster.createAllTables(db, ifNotExists);
                    }

                    @Override
                    public void onDropAllTables(Database db, boolean ifExists) {
                        DaoMaster.dropAllTables(db, ifExists);
                    }
                }, TestDbDao.class
        );
    }
}
