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
import com.mf.lightcontrol.databinding.DialogChooseLinkBinding;
import com.mf.lightcontrol.databinding.DialogEditNameBinding;

/**
 * Created by LiMing
 * Date 2018/12/13
 */
public class EditNameDialog extends Dialog {
    private DialogEditNameBinding mBinding;
    private Context mContext;
    private EditNameListener mEditNameListener;

    public EditNameDialog(@NonNull Context context) {
        super(context, R.style.DialogBaseStyle);
        mContext = context;
    }

    public void setEditNameListener(EditNameListener mEditNameListener) {
        this.mEditNameListener = mEditNameListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_edit_name, null, false);
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
        mBinding.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = mBinding.etName.getText().toString().trim();
                if (mEditNameListener != null) {
                    if (!TextUtils.isEmpty(trim)) {//
                        mEditNameListener.onName(trim);
                    } else {
                        Toast.makeText(mContext, "名称不能为空!", Toast.LENGTH_SHORT).show();
                    }
                }
                dismiss();
            }
        });
    }

    public interface EditNameListener {
        void onName(String name);

    }
}
