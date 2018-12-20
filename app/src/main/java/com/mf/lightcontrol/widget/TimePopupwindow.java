package com.mf.lightcontrol.widget;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;


import com.mf.lightcontrol.R;
import com.mf.lightcontrol.databinding.PopupwindowTimeLayoutBinding;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by Administrator on 2018/6/12.
 */

public class TimePopupwindow extends PopupWindow {
    private Activity aty;
    private PopupwindowTimeLayoutBinding mBinding;

    private TimeListener mTimeListener;

    public TimePopupwindow(Activity activity) {
        aty = activity;
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.popupwindow_time_layout, null, false);

        // 设置SelectPicPopupWindow的View
        this.setContentView(mBinding.getRoot());

        // 设置Popupwindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置Popupwindow弹出窗体的高
        //this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //点击空白处时，隐藏掉pop窗口
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(0.7f);
        //添加pop窗口关闭事件
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                dismiss();
                backgroundAlpha(1f);
            }
        });


        initView();
    }

    private void initView() {

        final ArrayList<String> dataList = new ArrayList<>();
        dataList.add("30分");
        dataList.add("不熄灭");
        dataList.add("30秒");
        mBinding.epvBody.setDataList(dataList);
        mBinding.epvBody.setOnScrollChangedListener(new EasyPickerView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(int curIndex) {

            }

            @Override
            public void onScrollFinished(int curIndex) {

            }
        });
        setState(true);

        mBinding.tvYushe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBinding.tvYushe.isSelected()) {
                    setState(true);
                }
            }
        });

        mBinding.tvCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBinding.tvCustom.isSelected()) {
                    setState(false);
                }
            }
        });
        mBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimeListener != null) {
                    mTimeListener.onTime();
                }
                dismiss();
            }
        });
    }

    private void setState(boolean bl) {
        mBinding.tvYushe.setSelected(bl);
        mBinding.tvCustom.setSelected(!bl);
        mBinding.flyYushe.setVisibility(bl ? View.VISIBLE : View.GONE);
        mBinding.llyCustom.setVisibility(!bl ? View.VISIBLE : View.GONE);
    }

    /**
     * 显示popupWindow
     */
    public void
    showPopupWindow() {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(getRootView(), Gravity.BOTTOM, 0, 0);
        } else {
            // this.dismiss();
        }
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = aty.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        aty.getWindow().setAttributes(lp);
    }

    private View getRootView() {
        return ((ViewGroup) aty.findViewById(android.R.id.content)).getChildAt(0);
    }

    public void setTimeListener(TimeListener TimeListener) {
        this.mTimeListener = TimeListener;
    }

    public interface TimeListener {
        void onTime();


    }

}
