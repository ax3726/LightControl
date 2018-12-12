package com.mf.lightcontrol.ui.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;
import com.mf.lightcontrol.R;
import com.mf.lightcontrol.databinding.ActivityControlSuccessBinding;

public class ControlSuccessActivity extends BaseActivity<BasePresenter, ActivityControlSuccessBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_control_success;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected boolean isTitleBar() {
        return true;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("配置完成");
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
