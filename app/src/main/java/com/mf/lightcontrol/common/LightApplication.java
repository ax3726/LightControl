package com.mf.lightcontrol.common;

import android.content.Context;
import android.content.Intent;

import com.lm.lib_common.base.ThisApplication;
import com.mf.lightcontrol.ui.config.DeviceListActivity;

/**
 * Created by LiMing
 * Date 2018/12/7
 */
public class LightApplication extends ThisApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if (System.currentTimeMillis() >= 1547949026000L) {//大于当前时间退出APP
            android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
            System.exit(0);   //常规java、c#的标准退出法，返回值为0代表正常退出

        }
    }

    @Override
    public void restartApp(Context context) {
        Intent intent = new Intent(context, DeviceListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
