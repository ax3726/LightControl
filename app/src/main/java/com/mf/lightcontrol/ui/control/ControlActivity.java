package com.mf.lightcontrol.ui.control;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.lm.lib_common.adapters.recyclerview.CommonAdapter;
import com.lm.lib_common.adapters.recyclerview.base.ViewHolder;
import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;
import com.lm.lib_common.utils.AppUtils;
import com.lm.lib_common.utils.ParseJsonUtils;
import com.mf.lightcontrol.R;
import com.mf.lightcontrol.common.PhoneClient;
import com.mf.lightcontrol.databinding.ActivityControlBinding;
import com.mf.lightcontrol.databinding.ItemLenthLayoutBinding;
import com.mf.lightcontrol.model.common.DeviceMessageModel;
import com.mf.lightcontrol.model.control.ControlModel;
import com.mf.lightcontrol.model.control.LenthModel;
import com.mf.lightcontrol.model.control.RedModel;
import com.mf.lightcontrol.model.control.SensorModel;
import com.mf.lightcontrol.model.control.SubmitModel;
import com.mf.lightcontrol.widget.ColorPickerView;
import com.mf.lightcontrol.widget.SeekArc;
import com.mf.lightcontrol.widget.TimePopupwindow;
import com.mf.lightcontrol.widget.dialog.DelSensorDialog;

import java.util.ArrayList;
import java.util.List;

public class ControlActivity extends BaseActivity<BasePresenter, ActivityControlBinding> {

    private boolean mIsSwitch = true;
    private List<SensorModel> mDataList = new ArrayList<>();
    private CommonAdapter<SensorModel> mAdapter;
    private int mPositionMax = 1024;
    private int mPositionPoMax = 1024;
    private int mTotalLength = 1024;//总长度
    private int mRunlLenth = 1024;//运行的灯珠长度
    private int mPutOutMin = 0;//熄灭时间
    private String mColor = "";//颜色
    private String mName = "";//名字

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
        mBinding.imgEffect.setOnClickListener(this);
        mBinding.imgLenth.setOnClickListener(this);
        mBinding.tvRight.setOnClickListener(this);
        mBinding.tvAddItem.setOnClickListener(this);
        mBinding.imgAllJia.setOnClickListener(this);
        mBinding.imgAllJian.setOnClickListener(this);
        mBinding.imgPaoJia.setOnClickListener(this);
        mBinding.imgPaoJian.setOnClickListener(this);
        mBinding.tvTime.setOnClickListener(this);
        mBinding.imgAllPosition.setOnClickListener(this);
        mBinding.imgPaoPosition.setOnClickListener(this);
        mBinding.imgSwitchBg.setOnColorChangedListenner(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int r, int g, int b) {
                mColor = toHexFromColor(r, g, b);
                ControlModel controlModel = new ControlModel();
                controlModel.setCommType(2);
                controlModel.setPara("Color");
                controlModel.setData(mColor);
                String str = ParseJsonUtils.getjsonStr(controlModel);
                PhoneClient.getIntance().send(str);//发送设置消息

            }

            @Override
            public void onMoveColor(int r, int g, int b) {

            }
        });


        if (TextUtils.isEmpty(mColor)) {
            mBinding.imgSwitchBg.getColor();
        } else {
            mBinding.imgSwitchBg.toColorPoint(mColor);
        }

        initTextSwitch();
        mBinding.rgBody.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ControlModel controlModel = new ControlModel();
                controlModel.setCommType(2);
                controlModel.setPara("Mode");
                switch (checkedId) {
                    case R.id.tv_liux://流行
                        controlModel.setData("1");
                        break;
                    case R.id.tv_lvdong://律动
                        controlModel.setData("2");
                        break;
                    case R.id.tv_huxi://呼吸
                        controlModel.setData("3");
                        break;
                    case R.id.tv_zhuguang://烛光
                        controlModel.setData("4");
                        break;
                    case R.id.tv_caihong://彩虹
                        controlModel.setData("5");
                        break;
                }

                String str = ParseJsonUtils.getjsonStr(controlModel);
                PhoneClient.getIntance().send(str);//发送设置消息


            }
        });
        mBinding.seekArc.setOnSeekArcChangeListener(new SeekArc.OnSeekArcChangeListener() {
            @Override
            public void onProgressChanged(SeekArc seekArc, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekArc seekArc) {

            }

            @Override
            public void onStopTrackingTouch(SeekArc seekArc) {
                ControlModel controlModel = new ControlModel();
                controlModel.setCommType(2);
                controlModel.setPara("Lum");
                controlModel.setData(seekArc.getProgress() + "");
                String str = ParseJsonUtils.getjsonStr(controlModel);
                PhoneClient.getIntance().send(str);//发送设置消息
            }
        });
        mBinding.sbSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                ControlModel controlModel = new ControlModel();
                controlModel.setCommType(2);
                controlModel.setPara("Speed");
                controlModel.setData(seekBar.getProgress() + "");
                String str = ParseJsonUtils.getjsonStr(controlModel);
                PhoneClient.getIntance().send(str);//发送设置消息
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_switch:
                mIsSwitch = !mIsSwitch;
                updateSwicth(mIsSwitch, true);
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
            case R.id.tv_right:
                submit();
                break;
            case R.id.img_all_position:
                submitLenth();
                break;
            case R.id.img_pao_position:
                submitLenth();
                break;
            case R.id.img_effect://效果配置
                if (!mBinding.imgEffect.isSelected()) {
                    setImgState(true);
                }
                break;
            case R.id.img_lenth://长度定位
                if (!mBinding.imgLenth.isSelected()) {
                    setImgState(false);
                }
                break;
            case R.id.tv_add_item:
                mDataList.add(new SensorModel());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.img_all_jia:
                if (mRunlLenth < mPositionMax) {
                    mTotalLength++;
                    mBinding.etAll.setText(String.valueOf(mTotalLength));
                }
                break;
            case R.id.img_all_jian:
                if (mTotalLength > 0) {
                    mTotalLength--;
                    mBinding.etAll.setText(String.valueOf(mTotalLength));
                }
                break;
            case R.id.img_pao_jia:
                if (mRunlLenth < mPositionPoMax) {
                    mRunlLenth++;
                    mBinding.etPao.setText(String.valueOf(mRunlLenth));
                }

                break;
            case R.id.img_pao_jian:
                if (mRunlLenth > 0) {
                    mRunlLenth--;
                    mBinding.etPao.setText(String.valueOf(mRunlLenth));
                }
                break;
            case R.id.tv_time:
                TimePopupwindow timePopupwindow = new TimePopupwindow(aty);
                timePopupwindow.setTimeListener(new TimePopupwindow.TimeListener() {
                    @Override
                    public void onTime(int min) {
                        mPutOutMin = min;
                        mBinding.tvTime.setText(min == 0 ? "不熄灭" : min + "分钟");

                        ControlModel controlModel = new ControlModel();
                        controlModel.setCommType(2);
                        controlModel.setPara("AtuoOffTime");
                        controlModel.setData(mPutOutMin + "");
                        String str = ParseJsonUtils.getjsonStr(controlModel);
                        PhoneClient.getIntance().send(str);//发送设置消息

                    }
                });
                timePopupwindow.showPopupWindow();
                break;
        }
    }

    private void initTextSwitch() {
        mBinding.etAll.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int num = Integer.valueOf(s.toString());
                    if (num > mPositionMax) {
                        mBinding.etAll.setText("" + mPositionMax);
                        mBinding.etAll.setSelection(4);
                    } else if (num < 0) {
                        mBinding.etAll.setText("" + mPositionMax);
                        mBinding.etAll.setSelection(4);
                    } else {
                        mTotalLength = num;
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.etPao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int num = Integer.valueOf(s.toString());
                    if (num > mPositionPoMax) {
                        mBinding.etPao.setText("" + mPositionPoMax);
                        mBinding.etPao.setSelection(4);
                    } else if (num < 0) {
                        mBinding.etPao.setText("" + mPositionPoMax);
                        mBinding.etPao.setSelection(4);
                    } else {
                        mRunlLenth = num;
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void updateSwicth(boolean bl, boolean send) {


        mBinding.tvLvdong.setChecked(bl);
        mBinding.tvHuxi.setChecked(bl);
        mBinding.tvZhuguang.setChecked(bl);
        mBinding.tvCaihong.setChecked(bl);
        mBinding.tvLiux.setChecked(bl);

        mBinding.tvLvdong.setClickable(bl);
        mBinding.tvHuxi.setClickable(bl);
        mBinding.tvZhuguang.setClickable(bl);
        mBinding.tvCaihong.setClickable(bl);
        mBinding.tvLiux.setClickable(bl);


        mBinding.imgSwitch.setSelected(bl);
        mBinding.imgSwitchBg.setVisibility(bl ? View.VISIBLE : View.GONE);
        mBinding.imgSwitchOffBg.setVisibility(!bl ? View.VISIBLE : View.GONE);
        mBinding.seekArc.setEnabled(bl);
        if (!bl) {
            mBinding.seekArc.setProgress(0);
        } else {
            mBinding.seekArc.setProgress(40);
        }
        if (send) {
            ControlModel controlModel = new ControlModel();
            controlModel.setCommType(2);
            controlModel.setPara("ONOFF");
            controlModel.setData(bl ? "Power" : "Sleep");
            String str = ParseJsonUtils.getjsonStr(controlModel);
            PhoneClient.getIntance().send(str);//发送设置消息
        }
    }

    @Override
    protected void initData() {
        super.initData();

        DeviceMessageModel model = getIntent().getParcelableExtra("data");
        mName = getIntent().getStringExtra("name");
        updateSwicth(true, false);
        setImgState(true);
        load(model);
    }

    private void load(DeviceMessageModel model) {
        if (model == null) {
            return;
        }
        mIsSwitch = model.getONOFFStatus().equals("Power");
        updateSwicth(mIsSwitch, false);

        mBinding.seekArc.setProgress(model.getLum());
        mBinding.sbSpeed.setProgress(model.getSpeed());
        mRunlLenth = model.getRunLenth();
        mTotalLength = model.getTotalLenth();

        mPutOutMin = model.getAtuoOffTime();
        mBinding.tvTime.setText(mPutOutMin == 0 ? "不熄灭" : mPutOutMin + "分钟");

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        initAdapter();
        initUDP();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    hideWaitDialog();
                    showToast("设置成功!");
                    startActivity(ControlSuccessActivity.class);
                    finish();
                    break;
                case 1://红外
                    hideWaitDialog();
                    showToast("设置成功!");
                    break;
                case 2://长度
                    hideWaitDialog();
                    showToast("设置成功!");
                    break;
            }
            return false;
        }
    });

    private void initUDP() {
        PhoneClient.getIntance().setUdpListener(new PhoneClient.UdpListener() {

            @Override
            public void onSetting() {
                mHandler.sendEmptyMessage(0);
            }

            @Override
            public void onRed() {
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onLenth() {
                mHandler.sendEmptyMessage(2);
            }
        });
    }

    private void initAdapter() {
        mAdapter = new CommonAdapter<SensorModel>(aty, R.layout.item_lenth_layout, mDataList) {
            @Override
            protected void convert(ViewHolder holder, SensorModel item, int position) {
                ItemLenthLayoutBinding binding = holder.getBinding(ItemLenthLayoutBinding.class);
                binding.tvNum.setText("传感器编号" + (position + 1));
                binding.etNum.setText(String.valueOf(item.getPosition()));
                binding.imgDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DelSensorDialog delSensorDialog = new DelSensorDialog(aty);
                        delSensorDialog.setDelSensorListener(new DelSensorDialog.DelSensorListener() {
                            @Override
                            public void onDel() {
                                submitRed("Delete", position, item.getPosition());
                                mDataList.remove(position);
                                notifyDataSetChanged();
                            }
                        });
                        delSensorDialog.show();
                    }
                });
                binding.imgJian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = mDataList.get(position).getPosition();
                        if (po > 1) {
                            po--;
                            mDataList.get(position).setPosition(po);
                        }
                        notifyDataSetChanged();
                    }
                });
                binding.imgJia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int po = mDataList.get(position).getPosition();
                        if (po < mPositionMax) {
                            po++;
                            mDataList.get(position).setPosition(po);
                        }
                        notifyDataSetChanged();
                    }
                });
                binding.imgPosition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitRed("Increase", position, item.getPosition());
                    }
                });

            }
        };
        mAdapter.setEmptyView(R.layout.empty_control_hint_layout);
        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcBody.setAdapter(mAdapter);
        mBinding.srlBody.setEnableRefresh(false);
        mBinding.srlBody.setEnableLoadmore(false);
    }

    private void setImgState(boolean bl) {
        mBinding.imgEffect.setSelected(bl);
        mBinding.imgLenth.setSelected(!bl);
        mBinding.llyEffect.setVisibility(bl ? View.VISIBLE : View.GONE);
        mBinding.llyLenth.setVisibility(!bl ? View.VISIBLE : View.GONE);
    }

    private void submit() {
        if (!mBinding.seekArc.isEnabled()) {
            return;
        }
        int type = 0;
        if (mBinding.tvLiux.isChecked()) {
            type = 1;
        } else if (mBinding.tvLvdong.isChecked()) {
            type = 2;
        } else if (mBinding.tvHuxi.isChecked()) {
            type = 3;
        } else if (mBinding.tvZhuguang.isChecked()) {
            type = 4;
        } else if (mBinding.tvCaihong.isChecked()) {
            type = 5;
        }
        if (type == 0) {
            showToast("数据有误!");
            return;
        }
        int speed = mBinding.sbSpeed.getProgress();//速度
        int lum = mBinding.seekArc.getProgress();//亮度

        SubmitModel submitModel = new SubmitModel();
        submitModel.setCommType(0);
        submitModel.setProduct(mName);
        submitModel.setMode(type);
        submitModel.setColor(mColor);
        submitModel.setSpeed(speed);
        submitModel.setLum(lum);
        submitModel.setAtuoOffTime(mPutOutMin);

        String str = ParseJsonUtils.getjsonStr(submitModel);

        PhoneClient.getIntance().send(str);//发送设置消息
        showWaitDialog("正在设置中...");
    }

    private void submitLenth() {
        LenthModel lenthModel = new LenthModel();
        lenthModel.setCommType(4);
        lenthModel.setTotalLenth(mTotalLength);
        lenthModel.setRunLenth(mRunlLenth);
        String str = ParseJsonUtils.getjsonStr(lenthModel);
        PhoneClient.getIntance().send(str);//发送设置消息
        showWaitDialog("正在设置中...");
    }

    private void submitRed(String type, int position, int size) {
        RedModel redModel = new RedModel();
        redModel.setCommType(3);
        redModel.setIRMapping(new RedModel.IRMappingBean(position, type, size));

        String str = ParseJsonUtils.getjsonStr(redModel);
        PhoneClient.getIntance().send(str);//发送设置消息
        showWaitDialog("正在设置中...");
    }


    /**
     * Color对象转换成字符串
     *
     * @return 16进制颜色字符串
     */
    private static String toHexFromColor(int red, int green, int blue) {
        String r, g, b;
        StringBuilder su = new StringBuilder();
        r = Integer.toHexString(red);
        g = Integer.toHexString(green);
        b = Integer.toHexString(blue);
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;
        r = r.toUpperCase();
        g = g.toUpperCase();
        b = b.toUpperCase();
        su.append("0x");
        su.append(r);
        su.append(g);
        su.append(b);
        //0xFF0000FF
        return su.toString();
    }


}
