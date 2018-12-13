package com.mf.lightcontrol.ui.control;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;
import com.mf.lightcontrol.R;
import com.mf.lightcontrol.databinding.ActivityControlBinding;

public class ControlActivity extends BaseActivity<BasePresenter, ActivityControlBinding> {

    private boolean mIsSwitch = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_control;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.imgSwitch.setOnClickListener(this);
        mBinding.imgMax.setOnClickListener(this);
        mBinding.imgMin.setOnClickListener(this);
        mBinding.llyLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_switch:
                mIsSwitch = !mIsSwitch;
                updateSwicth(mIsSwitch);
                break;
            case R.id.img_min:
                if (mIsSwitch) {
                    mBinding.seekArc.setProgress(0);
                }
                break;
            case R.id.img_max:
                if (mIsSwitch) {
                    mBinding.seekArc.setProgress(100);
                }
                break;
            case R.id.lly_left:
                finish();
                break;

        }
    }

    private void updateSwicth(boolean bl) {
        mBinding.tvLiux.setSelected(bl);
        mBinding.tvLvdong.setSelected(bl);
        mBinding.tvHuxi.setSelected(bl);
        mBinding.tvZhuguang.setSelected(bl);
        mBinding.tvCaihong.setSelected(bl);
        mBinding.imgSwitch.setSelected(bl);
        mBinding.imgSwitchBg.setSelected(bl);
        mBinding.seekArc.setEnabled(bl);
        if (!bl) {
            mBinding.seekArc.setProgress(0);
        } else {
            mBinding.seekArc.setProgress(30);
        }

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        updateSwicth(true);
    }

}
