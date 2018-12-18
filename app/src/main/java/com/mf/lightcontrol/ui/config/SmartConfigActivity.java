package com.mf.lightcontrol.ui.config;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;

import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;
import com.mf.lightcontrol.R;
import com.mf.lightcontrol.databinding.ActivitySmartConfigBinding;

public class SmartConfigActivity extends BaseActivity<BasePresenter, ActivitySmartConfigBinding> {
    private AnimationDrawable animationDrawable = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smart_config;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isTitleBar() {
        return true;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("配置");
    }


    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.tvConfig.setOnClickListener(this);
        mBinding.imgWifi.setOnClickListener(this);
        mBinding.imgEye.setOnClickListener(this);
        mBinding.imgDel.setOnClickListener(this);
        mBinding.etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBinding.imgDel.setVisibility(!TextUtils.isEmpty(s) ? View.VISIBLE : View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_config:
                setFrameAnim();
                break;
            case R.id.img_wifi:
                stopFrameAnim();
                break;
            case R.id.img_eye:
                if (mBinding.imgEye.isSelected()) {
                    mBinding.imgEye.setSelected(false);
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    mBinding.etPwd.setTransformationMethod(method);
                } else {
                    mBinding.imgEye.setSelected(true);
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    mBinding.etPwd.setTransformationMethod(method1);
                }
                break;
            case R.id.img_del:
                mBinding.etPwd.setText("");
                break;
        }
    }

    /**
     * 开始帧动画
     */
    private void setFrameAnim() {
        animationDrawable = (AnimationDrawable) getResources().getDrawable(
                R.drawable.anim_wifi);
        mBinding.imgWifi.setBackground(animationDrawable);
        animationDrawable.start();
    }

    /**
     * 停止帧动画
     */
    private void stopFrameAnim() {
        if (animationDrawable != null && animationDrawable.isRunning()) {
            animationDrawable.stop();
            mBinding.imgWifi.setBackgroundResource(R.drawable.config_wifi1_icon);
        }

    }

}
