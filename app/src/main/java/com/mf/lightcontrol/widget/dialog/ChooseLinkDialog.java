package com.mf.lightcontrol.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.mf.lightcontrol.R;
import com.mf.lightcontrol.databinding.DialogChooseLinkBinding;

/**
 * Created by LiMing
 * Date 2018/12/13
 */
public class ChooseLinkDialog extends Dialog {
    private DialogChooseLinkBinding mBinding;
    private Context mContext;
    private ChooseLinkListener mChooseLinkListener;

    public ChooseLinkDialog(@NonNull Context context) {
        super(context, R.style.DialogBaseStyle);
        mContext = context;
    }

    public void setChooseLinkListener(ChooseLinkListener mChooseLinkListener) {
        this.mChooseLinkListener = mChooseLinkListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_choose_link, null, false);
        this.setContentView(mBinding.getRoot());

        WindowManager m = ((Activity) mContext).getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = (int) ((d.getWidth()) * 0.8);
        params.height = params.height;
        this.getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        mBinding.imgSmart.setSelected(true);
        mBinding.tvSmart.setSelected(true);


        mBinding.tvApHint.setText("该模式下，断开手机与路由器连接，手动\n连接以“HomeJoy”开头的设备WiFi热点。");
        mBinding.rlySmart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.imgSmart.setSelected(true);
                mBinding.tvSmart.setSelected(true);

                mBinding.imgAp.setSelected(false);
                mBinding.tvAp.setSelected(false);
                mBinding.tvApHint.setSelected(false);
            }
        });
        mBinding.rlyAp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.imgAp.setSelected(true);
                mBinding.tvAp.setSelected(true);
                mBinding.tvApHint.setSelected(true);

                mBinding.imgSmart.setSelected(false);
                mBinding.tvSmart.setSelected(false);
                if (mChooseLinkListener != null) {
                    mChooseLinkListener.onApSelect();
                }
            }
        });

        mBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mChooseLinkListener != null) {
                    if (mBinding.imgSmart.isSelected()) {//
                        mChooseLinkListener.onSmart();
                    } else {
                        mChooseLinkListener.onAp();
                    }
                }
                dismiss();
            }
        });
    }

    public interface ChooseLinkListener {
        void onSmart();

        void onAp();

        void onApSelect();
    }
}
