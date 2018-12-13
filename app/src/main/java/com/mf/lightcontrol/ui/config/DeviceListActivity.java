package com.mf.lightcontrol.ui.config;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lm.lib_common.adapters.recyclerview.CommonAdapter;
import com.lm.lib_common.adapters.recyclerview.base.ViewHolder;
import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;
import com.mf.lightcontrol.R;
import com.mf.lightcontrol.databinding.ActivityDeviceListBinding;
import com.mf.lightcontrol.databinding.ItemDeviceListBinding;
import com.mf.lightcontrol.ui.control.ControlActivity;
import com.mf.lightcontrol.widget.dialog.ChooseLinkDialog;
import com.mf.lightcontrol.widget.dialog.EditNameDialog;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends BaseActivity<BasePresenter, ActivityDeviceListBinding> {

    private List<String> mDataList = new ArrayList<>();
    private CommonAdapter<String> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_list;
    }

    @Override
    protected boolean isTitleBar() {
        return true;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("HOMEJOY 智能灯带");
        mTitleBarLayout.setRightTxt("下一页");
        mTitleBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ControlActivity.class);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initData() {
        super.initData();
        mDataList.add("");
        mDataList.add("");
        mDataList.add("");
        mDataList.add("");

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mAdapter = new CommonAdapter<String>(aty, R.layout.item_device_list, mDataList) {
            @Override
            protected void convert(ViewHolder holder, String item, int position) {
                ItemDeviceListBinding binding = holder.getBinding(ItemDeviceListBinding.class);
                binding.imgEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditNameDialog nameDialog=new EditNameDialog(aty);
                        nameDialog.setEditNameListener(new EditNameDialog.EditNameListener() {
                            @Override
                            public void onName(String name) {

                            }
                        });
                        nameDialog.show();
                    }
                });
            }
        };

        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcBody.setAdapter(mAdapter);
        mBinding.rcBody.setNestedScrollingEnabled(false);
        mBinding.srlBody.setEnableRefresh(false);
        mBinding.srlBody.setEnableLoadmore(false);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseLinkDialog linkDialog = new ChooseLinkDialog(aty);
                linkDialog.setChooseLinkListener(new ChooseLinkDialog.ChooseLinkListener() {
                    @Override
                    public void onSmart() {

                    }

                    @Override
                    public void onAp() {

                    }
                });
                linkDialog.show();
            }
        });
    }
}
