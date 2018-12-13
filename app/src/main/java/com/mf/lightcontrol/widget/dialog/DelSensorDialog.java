package com.mf.lightcontrol.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.mf.lightcontrol.R;
import com.mf.lightcontrol.databinding.DialogDelSensorBinding;
import com.mf.lightcontrol.databinding.DialogDelSensorBinding;

/**
 * Created by LiMing
 * Date 2018/12/13
 */
public class DelSensorDialog extends Dialog {
    private DialogDelSensorBinding mBinding;
    private Context mContext;
    private DelSensorListener mDelSensorListener;

    public DelSensorDialog(@NonNull Context context) {
        super(context, R.style.DialogBaseStyle);
        mContext = context;
    }

    public void setDelSensorListener(DelSensorListener mDelSensorListener) {
        this.mDelSensorListener = mDelSensorListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_del_sensor, null, false);
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
        mBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBinding.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDelSensorListener != null) {
                    mDelSensorListener.onDel();
                }
                dismiss();
            }
        });
    }

    public interface DelSensorListener {
        void onDel();

    }
}
