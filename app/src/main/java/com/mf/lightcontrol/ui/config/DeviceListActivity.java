package com.mf.lightcontrol.ui.config;

import android.os.Handler;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lm.lib_common.adapters.recyclerview.CommonAdapter;
import com.lm.lib_common.adapters.recyclerview.base.ViewHolder;
import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;
import com.mf.lightcontrol.R;
import com.mf.lightcontrol.common.PhoneClient;
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
        PhoneClient.getIntance().init();//初始化UDP通讯
        PhoneClient.getIntance().setSearchListener(new PhoneClient.SearchListener() {
            @Override
            public void onDevice(String ip) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                        EditNameDialog nameDialog = new EditNameDialog(aty);
                        nameDialog.setEditNameListener(new EditNameDialog.EditNameListener() {
                            @Override
                            public void onName(String name) {

                            }
                        });
                        nameDialog.show();
                    }
                });
                binding.rlyItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhoneClient.getIntance().setSendIP("192.168.1.103");
                      showWaitDialog("正在配置中....");
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              hideWaitDialog();

                              startActivity(ControlActivity.class);
                          }
                      },2000);
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
                        startActivity(SmartConfigActivity.class);
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
