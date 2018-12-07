package com.lm.lib_common.base;

import android.app.Application;
import android.content.Context;


/**
 * Created by LiMing on 2016/11/12.
 */
public abstract class ThisApplication extends Application {
    public static Context applicationContext;
    private static ThisApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        instance = this;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static ThisApplication getInstance() {
        return instance;
    }

    public  abstract void restartApp(Context context);
}
