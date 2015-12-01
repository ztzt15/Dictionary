package com.zt.dictionary.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zt.dictionary.util.netUtil.YeePayBaseRequest;

import org.litepal.LitePalApplication;
import org.litepal.tablemanager.Connector;


/**
 * @user: zt
 * @describe:
 */


public class MyApplication extends LitePalApplication {
    private static MyApplication instance;

    public MyApplication() {
        instance = this;
    }

    public static Application getContext() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        SharedPreferences sp = this.getSharedPreferences("HOST APPLICATION", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("keyyy", "valueee");
//        editor.commit();
        YeePayBaseRequest.init(instance);
        LitePalApplication.initialize(this);
    }
}
