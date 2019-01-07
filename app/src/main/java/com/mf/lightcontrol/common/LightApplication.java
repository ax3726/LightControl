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
    }

    @Override
    public void restartApp(Context context) {
     /*   Intent intent = new Intent(context, DeviceListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());*/
    }
}
