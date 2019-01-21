package com.mf.lightcontrol.ui.config;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.lm.lib_common.adapters.recyclerview.CommonAdapter;
import com.lm.lib_common.adapters.recyclerview.base.ViewHolder;
import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;
import com.lm.lib_common.utils.AppUtils;
import com.lm.lib_common.utils.ParseJsonUtils;
import com.mf.lightcontrol.R;
import com.mf.lightcontrol.common.PhoneClient;
import com.mf.lightcontrol.databinding.ActivityDeviceListBinding;
import com.mf.lightcontrol.databinding.ItemDeviceListBinding;
import com.mf.lightcontrol.model.common.DeviceMessageModel;
import com.mf.lightcontrol.model.common.LoadMessageModel;
import com.mf.lightcontrol.model.control.ControlModel;
import com.mf.lightcontrol.model.control.DeviceModel;
import com.mf.lightcontrol.ui.control.ControlActivity;
import com.mf.lightcontrol.widget.dialog.ChooseLinkDialog;
import com.mf.lightcontrol.widget.dialog.EditNameDialog;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends BaseActivity<BasePresenter, ActivityDeviceListBinding> {

    private List<DeviceModel> mDataList = new ArrayList<>();
    private CommonAdapter<DeviceModel> mAdapter;

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

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    hideWaitDialog();
                    break;
                case 0:
                    addDevice((DeviceModel) msg.obj);
                    break;
            }

            return false;
        }
    });

    @Override
    protected void initData() {
        super.initData();
        AppUtils.getInstance().setAppStatus(AppUtils.STATUS_NORMAL);//设置正常状态
        PhoneClient.getIntance().init();//初始化UDP通讯
        PhoneClient.getIntance().setSearchListener(new PhoneClient.SearchListener() {
            @Override
            public void onDevice(String ip, String name) {
                Message message = new Message();
                message.obj = new DeviceModel(name, ip);
                message.what = 0;
                mHandler.sendMessage(message);
            }


        });
    }

    private void addDevice(DeviceModel ip) {
        boolean bl = true;
        for (DeviceModel model : mDataList) {
            if (model.getIp().equals(ip.getIp())) {
                bl = false;
                break;
            }
        }
        if (bl) {
            mDataList.add(ip);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        PhoneClient.getIntance().startSearch();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PhoneClient.getIntance().stopSearch();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mAdapter = new CommonAdapter<DeviceModel>(aty, R.layout.item_device_list, mDataList) {
            @Override
            protected void convert(ViewHolder holder, DeviceModel item, int position) {

                ItemDeviceListBinding binding = holder.getBinding(ItemDeviceListBinding.class);
                binding.tvName.setText(TextUtils.isEmpty(item.getName()) ? "未知设备" + (position + 1) : item.getName());
                binding.imgEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditNameDialog nameDialog = new EditNameDialog(aty);
                        nameDialog.setEditNameListener(new EditNameDialog.EditNameListener() {
                            @Override
                            public void onName(String name) {
                                mDataList.get(position).setName(name);
                                ControlModel controlModel = new ControlModel();
                                controlModel.setCommType(2);
                                controlModel.setPara("Product");
                                controlModel.setData(name);
                                String str = ParseJsonUtils.getjsonStr(controlModel);
                                PhoneClient.getIntance().send(str);//发送设置消息
                                notifyDataSetChanged();
                            }
                        });
                        nameDialog.show();
                    }
                });
                binding.rlyItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PhoneClient.getIntance().setSendIP(item.getIp());
                        PhoneClient.getIntance().send(ParseJsonUtils.getjsonStr(new LoadMessageModel()));
                        PhoneClient.getIntance().setDeviceListener(new PhoneClient.DeviceListener() {
                            @Override
                            public void onDevice(DeviceMessageModel model) {
                                mHandler.sendEmptyMessage(1);
                                startActivity(new Intent(aty, ControlActivity.class)
                                        .putExtra("data", model)
                                        .putExtra("name", item.getName()));
                            }
                        });
                        showWaitDialog("读取设备信息中...");


                    }
                });
            }
        };

        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcBody.setAdapter(mAdapter);
        mBinding.rcBody.setNestedScrollingEnabled(false);
        mBinding.srlBody.setEnableRefresh(false);
        mBinding.srlBody.setEnableLoadmore(false);
        mAdapter.setEmptyView(R.layout.empty_control_hint_layout, "正在搜索附近的设备...\n您可点击下方按钮添加设备!");
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

                    @Override
                    public void onApSelect() {
                        mDataList.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                });
                linkDialog.show();
            }
        });
    }


}
