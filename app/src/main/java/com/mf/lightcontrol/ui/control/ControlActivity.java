package com.mf.lightcontrol.ui.control;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.lm.lib_common.adapters.recyclerview.CommonAdapter;
import com.lm.lib_common.adapters.recyclerview.base.ViewHolder;
import com.lm.lib_common.base.BaseActivity;
import com.lm.lib_common.base.BasePresenter;
import com.lm.lib_common.utils.ParseJsonUtils;
import com.mf.lightcontrol.R;
import com.mf.lightcontrol.common.PhoneClient;
import com.mf.lightcontrol.databinding.ActivityControlBinding;
import com.mf.lightcontrol.databinding.ItemLenthLayoutBinding;
import com.mf.lightcontrol.model.common.DeviceMessageModel;
import com.mf.lightcontrol.model.control.ControlModel;
import com.mf.lightcontrol.model.control.ControlModel1;
import com.mf.lightcontrol.model.control.RedModel;
import com.mf.lightcontrol.model.control.SensorModel;
import com.mf.lightcontrol.model.control.SubmitModel;
import com.mf.lightcontrol.utils.SoftKeyBoardListener;
import com.mf.lightcontrol.widget.ColorPickerSeekView;
import com.mf.lightcontrol.widget.ColorPickerView;
import com.mf.lightcontrol.widget.SeekArc;
import com.mf.lightcontrol.widget.dialog.DelSensorDialog;

import java.util.ArrayList;
import java.util.List;

public class ControlActivity extends BaseActivity<BasePresenter, ActivityControlBinding> {

    private boolean mIsSwitch = true;
    private boolean mIsFirst = false;
    private List<SensorModel> mDataList = new ArrayList<>();
    private CommonAdapter<SensorModel> mAdapter;

    private int mTransAdvanceShow = 0;//横梯的提前显示步数
    private int mTransAdvanceShowMax = 1024;//横梯的提前显示步数最大值
    private int mDetecStopTime = 0;//感应停止时间
    private int mTotalLength = 1024;//总长度
    //private int mPutOutMin = 0;//熄灭时间
    private String mColor = "";//颜色
    private String mName = "";//名字
    private String mSignal = "";//灯带的控制信号 （取值“RGB”、“RBG”、“GRB”、“ GBR”、“ BRG”、“ BGR”）
    private String mTransDirDetecColor = "";//横梯的感应颜色种类  （取值“SolidColor”、” Colours”）
    private int mLum = 0;
    private int mSpeed = 0;//速度
    private int mRunLenthPram = 0;//长度
    private int mMode = 0;
    private boolean mIsMode = false;//是否设置模式


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
        mBinding.imgTiJia.setOnClickListener(this);
        mBinding.imgTiJian.setOnClickListener(this);
        mBinding.imgGanJia.setOnClickListener(this);
        mBinding.imgGanJian.setOnClickListener(this);
        mBinding.imgTiPosition.setOnClickListener(this);
        mBinding.imgGanPosition.setOnClickListener(this);
        mBinding.imgDengXinPosition.setOnClickListener(this);
        mBinding.imgGanColorPosition.setOnClickListener(this);
        mBinding.tvDengXin.setOnClickListener(this);
        mBinding.tvGanColor.setOnClickListener(this);
        mBinding.imgSwitchBg.setOnColorChangedListenner(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int r, int g, int b) {
                String color = toHexFromColor(r, g, b);
                if (!"0x000000".equals(color)) {
                    mColor = color;
                    ControlModel controlModel = new ControlModel();
                    controlModel.setCommType(2);
                    controlModel.setPara("Color");
                    controlModel.setData(mColor);
                    String str = ParseJsonUtils.getjsonStr(controlModel);
                    PhoneClient.getIntance().send(str);//发送设置消息
                }

            }

            @Override
            public void onMoveColor(int r, int g, int b) {

            }
        });


        initTextSwitch();
        mBinding.rgBody.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (!mIsMode) {
                    ControlModel1 controlModel = new ControlModel1();
                    controlModel.setCommType(2);
                    controlModel.setPara("Mode");
                    switch (checkedId) {
                        case R.id.tv_liux://流行

                            controlModel.setData(1);
                            break;
                        case R.id.tv_lvdong://律动
                            controlModel.setData(2);
                            break;
                        case R.id.tv_huxi://呼吸
                            controlModel.setData(3);
                            break;
                        case R.id.tv_caihong://彩虹
                            controlModel.setData(4);
                            break;
                        case R.id.tv_quanguan://全关
                            controlModel.setData(5);
                            break;
                    }
                    if (controlModel.getData() > 0) {
                        String str = ParseJsonUtils.getjsonStr(controlModel);
                        PhoneClient.getIntance().send(str);//发送设置消息
                    }
                    mIsFirst = true;
                } else {
                    mIsMode = false;
                    if (!mIsFirst && mIsSwitch) {
                        ControlModel1 controlModel = new ControlModel1();
                        controlModel.setCommType(2);
                        controlModel.setPara("Mode");
                        switch (checkedId) {
                            case R.id.tv_liux://流行

                                controlModel.setData(1);
                                break;
                            case R.id.tv_lvdong://律动
                                controlModel.setData(2);
                                break;
                            case R.id.tv_huxi://呼吸
                                controlModel.setData(3);
                                break;
                            case R.id.tv_caihong://彩虹
                                controlModel.setData(4);
                                break;
                            case R.id.tv_quanguan://全关
                                controlModel.setData(5);
                                break;
                        }
                        if (controlModel.getData() > 0) {
                            String str = ParseJsonUtils.getjsonStr(controlModel);
                            PhoneClient.getIntance().send(str);//发送设置消息
                        }
                        mIsFirst = true;
                    }
                }
                switch (checkedId) {
                    case R.id.tv_liux://流行

                        mMode = 1;
                        break;
                    case R.id.tv_lvdong://律动
                        mMode = 2;
                        break;
                    case R.id.tv_huxi://呼吸
                        mMode = 3;
                        break;
                    case R.id.tv_caihong://彩虹
                        mMode = 4;
                        break;
                    case R.id.tv_quanguan://全关
                        mMode = 5;
                        break;

                }
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
                mLum = seekArc.getProgress();
                ControlModel1 controlModel = new ControlModel1();
                controlModel.setCommType(2);
                controlModel.setPara("Lum");
                controlModel.setData(seekArc.getProgress());
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
                mSpeed = seekBar.getProgress();
                ControlModel1 controlModel = new ControlModel1();
                controlModel.setCommType(2);
                controlModel.setPara("Speed");
                controlModel.setData(seekBar.getProgress());
                String str = ParseJsonUtils.getjsonStr(controlModel);
                PhoneClient.getIntance().send(str);//发送设置消息
            }
        });
        mBinding.sbLenth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mRunLenthPram = seekBar.getProgress();
                ControlModel1 controlModel = new ControlModel1();
                controlModel.setCommType(2);
                controlModel.setPara("RunLenth");
                controlModel.setData(seekBar.getProgress());
                String str = ParseJsonUtils.getjsonStr(controlModel);
                PhoneClient.getIntance().send(str);//发送设置消息
            }
        });
        mBinding.picker1.setOnColorPickerChangeListener(new ColorPickerSeekView.OnColorPickerChangeListener() {
            @Override
            public void onColorChanged(ColorPickerSeekView picker, int color) {

            }

            @Override
            public void onStartTrackingTouch(ColorPickerSeekView picker) {

            }

            @Override
            public void onStopTrackingTouch(ColorPickerSeekView picker) {
                if ("SolidColor".equals(mTransDirDetecColor)) {
                    String color = picker.getColor();
                    ControlModel controlModel1 = new ControlModel();
                    controlModel1.setCommType(2);
                    controlModel1.setPara("TransDirDetecSolidColor");
                    controlModel1.setData(color);
                    String str1 = ParseJsonUtils.getjsonStr(controlModel1);
                    PhoneClient.getIntance().send(str1);//发送设置消息
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_switch:
                mIsSwitch = !mIsSwitch;
                updateSwicth(mIsSwitch, true, false);
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
            case R.id.img_ti_position:
                submitLenth(true);
                break;
            case R.id.img_gan_position:
                submitLenth(false);
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
                mDataList.add(new SensorModel(mDataList.size()));
                mAdapter.notifyDataSetChanged();
                break;


            case R.id.img_ti_jia:
                if (mTransAdvanceShow < mTransAdvanceShowMax) {
                    mTransAdvanceShow++;
                    mBinding.etTi.setText(String.valueOf(mTransAdvanceShow));
                }

                break;
            case R.id.img_ti_jian:
                if (mTransAdvanceShow > 0) {
                    mTransAdvanceShow--;
                    mBinding.etTi.setText(String.valueOf(mTransAdvanceShow));
                }
                break;
            case R.id.img_gan_jia:
                mDetecStopTime++;
                mBinding.etGan.setText(String.valueOf(mDetecStopTime));
                break;
            case R.id.img_gan_jian:
                if (mDetecStopTime > 0) {
                    mDetecStopTime--;
                    mBinding.etGan.setText(String.valueOf(mDetecStopTime));
                }
                break;
          /*  case R.id.tv_time:
                TimePopupwindow timePopupwindow = new TimePopupwindow(aty);
                timePopupwindow.setTimeListener(new TimePopupwindow.TimeListener() {
                    @Override
                    public void onTime(int min) {
                        mPutOutMin = min;
                        mBinding.tvTime.setText(min == 0 ? "不熄灭" : min + "分钟");

                        ControlModel1 controlModel = new ControlModel1();
                        controlModel.setCommType(2);
                        controlModel.setPara("AtuoOffTime");
                        controlModel.setData(mPutOutMin);
                        String str = ParseJsonUtils.getjsonStr(controlModel);
                        PhoneClient.getIntance().send(str);//发送设置消息

                    }
                });
                timePopupwindow.showPopupWindow();
                break;*/
            case R.id.tv_deng_xin:
                showDengXin();
                break;
            case R.id.tv_gan_color:
                showGanColor();
                break;
            case R.id.img_deng_xin_position://灯带信号
                ControlModel controlModel = new ControlModel();
                controlModel.setCommType(2);
                controlModel.setPara("Signal");
                controlModel.setData(mSignal);
                String str = ParseJsonUtils.getjsonStr(controlModel);
                PhoneClient.getIntance().send(str);//发送设置消息
                break;
            case R.id.img_gan_color_position://灯带信号
                ControlModel controlModel1 = new ControlModel();
                controlModel1.setCommType(2);
                controlModel1.setPara("TransDirDetecColor");
                controlModel1.setData(mTransDirDetecColor);
                String str1 = ParseJsonUtils.getjsonStr(controlModel1);
                PhoneClient.getIntance().send(str1);//发送设置消息
                break;
        }
    }

    private void initTextSwitch() {
        mBinding.etTi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
                    int num = Integer.valueOf(s.toString());
                    if (num > mTransAdvanceShowMax) {
                        mBinding.etTi.setText("" + mTransAdvanceShowMax);
                        mBinding.etTi.setSelection(4);
                    } else if (num < 0) {
                        mBinding.etTi.setText("" + mTransAdvanceShowMax);
                        mBinding.etTi.setSelection(4);
                    } else {
                        mTransAdvanceShow = num;
                    }


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.etGan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    int num = Integer.valueOf(s.toString());
                    mDetecStopTime = num;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    /**
     * 设置颜色条得状态
     *
     * @param bl
     */
    private void setPickerState(boolean bl) {
        if (bl) {
            mBinding.picker1.setEnabled(true);
            mBinding.picker1.setColors(mBinding.picker1.createDefaultColorTable());
        } else {

            mBinding.picker1.setColors(new int[]{
                    Color.rgb(204, 204, 204),
                    Color.rgb(204, 204, 204),
                    Color.rgb(204, 204, 204)
            });
            mBinding.picker1.setEnabled(false);
        }

    }

    private void updateSwicth(boolean bl, boolean send, boolean first) {

        mIsMode = true;

        setMode(mMode, bl, first);
        mBinding.tvLvdong.setClickable(bl);
        mBinding.tvHuxi.setClickable(bl);
        mBinding.tvQuanguan.setClickable(bl);
        mBinding.tvCaihong.setClickable(bl);
        mBinding.tvLiux.setClickable(bl);

        mBinding.imgSwitch.setSelected(bl);
        mBinding.imgSwitchBg.setVisibility(bl ? View.VISIBLE : View.GONE);
        mBinding.imgSwitchOffBg.setVisibility(!bl ? View.VISIBLE : View.GONE);
        mBinding.seekArc.setEnabled(bl);
        if (!bl) {
//            mBinding.tvTime.setText("不熄灭");
            mBinding.sbLenth.setProgress(0);
            mBinding.sbSpeed.setProgress(0);
            mBinding.seekArc.setProgress(0);
        } else {
//            mBinding.tvTime.setText(mPutOutMin == 0 ? "不熄灭" : mPutOutMin + "分钟");
            mBinding.sbSpeed.setProgress(mSpeed);
            mBinding.seekArc.setProgress(mLum);
            mBinding.sbLenth.setProgress(mRunLenthPram);
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


        DeviceMessageModel model = (DeviceMessageModel) getIntent().getSerializableExtra("data");
        mName = getIntent().getStringExtra("name");
//        PhoneClient.getIntance().setSendIP("192.168.1.102");
        setImgState(true);
        load(model);


    }

    private void load(DeviceMessageModel model) {
//        mBinding.imgSwitchBg.toColorPoint("ffffff");
        if (model == null) {
            return;
        }


        mMode = model.getMode();

        mLum = model.getLum();
        mSpeed = model.getSpeed();
        mColor=model.getColor();
        mBinding.seekArc.setProgress(model.getLum());
        mBinding.sbSpeed.setProgress(model.getSpeed());
        mBinding.sbLenth.setProgress(model.getRunLenth());


        mRunLenthPram = model.getRunLenth();
        mDetecStopTime = model.getDetecStopTime();
        mTransAdvanceShow = model.getTransAdvanceShow();
        mSignal = model.getSignal();
        mTransDirDetecColor = model.getTransDirDetecColor();

        mBinding.etTi.setText(mTransAdvanceShow + "");
        mBinding.etGan.setText(mDetecStopTime + "");
        mBinding.tvGanColor.setText("SolidColor".equals(mTransDirDetecColor) ? "单色" : "七彩");
        mBinding.tvDengXin.setText(mSignal + "");

        setPickerState("SolidColor".equals(mTransDirDetecColor));
//        mPutOutMin = model.getAtuoOffTime();
//        mBinding.tvTime.setText(mPutOutMin == 0 ? "不熄灭" : mPutOutMin + "分钟");

        //  开关注释
      //  mIsSwitch = "Power".equals(model.getONOFF());
        updateSwicth(mIsSwitch, false, true);

        if (TextUtils.isEmpty(mColor)) {
            //  mBinding.imgSwitchBg.getColor();
        } else {
            mBinding.imgSwitchBg.toColorPoint(mColor);
        }


        List<List<Integer>> irMapping = model.getIRMapping();
        if (irMapping != null) {
            for (List<Integer> item : irMapping) {
                if (item != null && item.size() > 1) {
                    mDataList.add(new SensorModel(item.get(1), item.get(0)));
                }
            }
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }

        }

    }

    private void setMode(int model, boolean bl, boolean first) {
        if (!bl && !first) {
            mBinding.rgBody.clearCheck();
        } else {
            switch (model) {
                case 1:
                    mBinding.tvLiux.setChecked(bl);
                    break;
                case 2:
                    mBinding.tvLvdong.setChecked(bl);
                    break;
                case 3:
                    mBinding.tvHuxi.setChecked(bl);
                    break;
                case 4:
                    mBinding.tvCaihong.setChecked(bl);
                    break;
                case 5:
                    mBinding.tvQuanguan.setChecked(bl);

                    break;
            }
        }


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
                    //   hideWaitDialog();
                    showToast("设置成功!");
//                    startActivity(ControlSuccessActivity.class);
//                    finish();
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
                if (binding.etNum.getTag() != null) {
                    TextWatcher txtW = (TextWatcher) binding.etNum.getTag();
                    binding.etNum.removeTextChangedListener(txtW);
                }
                binding.tvNum.setText("传感器编号" + item.getId());
                binding.etNum.setText(String.valueOf(item.getPosition()));
                binding.imgDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DelSensorDialog delSensorDialog = new DelSensorDialog(aty);
                        delSensorDialog.setDelSensorListener(new DelSensorDialog.DelSensorListener() {
                            @Override
                            public void onDel() {
                                submitRed("Delete", item.getId(), item.getPosition());
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
                        if (po < 1024) {
                            po++;
                            mDataList.get(position).setPosition(po);
                        }
                        notifyDataSetChanged();
                    }
                });
                binding.imgPosition.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitRed("Increase", item.getId(), item.getPosition());
                    }
                });
                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!TextUtils.isEmpty(s)) {
                            int num = Integer.valueOf(s.toString());
                            if (num > 1024) {
                                binding.etNum.setText("" + 1024);
                                binding.etNum.setSelection(4);
                            } else if (num < 0) {
                                binding.etNum.setText("" + 1024);
                                binding.etNum.setSelection(4);
                            } else {
                                mDataList.get(position).setPosition(num);
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                };
                binding.etNum.addTextChangedListener(textWatcher);
                binding.etNum.setTag(textWatcher);
            }
        };
        mAdapter.setEmptyView(R.layout.empty_control_hint_layout);
        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcBody.setAdapter(mAdapter);
        mBinding.srlBody.setEnableRefresh(false);
        mBinding.srlBody.setEnableLoadmore(false);
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                if (mAdapter != null) {
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

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

        } else if (mBinding.tvCaihong.isChecked()) {
            type = 4;
        } else if (mBinding.tvQuanguan.isChecked()) {
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
//        submitModel.setAtuoOffTime(mPutOutMin);

        String str = ParseJsonUtils.getjsonStr(submitModel);

        PhoneClient.getIntance().send(str);//发送设置消息
        showWaitDialog("正在设置中...");
    }

    private void submitLenth(boolean is_add) {
        ControlModel1 controlModel = new ControlModel1();
        controlModel.setCommType(2);
        if (is_add) {
            controlModel.setPara("TransAdvanceShow");
            controlModel.setData(mTransAdvanceShow);
        } else {
            controlModel.setPara("DetecStopTime");
            controlModel.setData(mDetecStopTime);
        }
        String str = ParseJsonUtils.getjsonStr(controlModel);
        PhoneClient.getIntance().send(str);//发送设置消息
    }

    private void submitRed(String type, int position, int size) {
        RedModel redModel = new RedModel();
        redModel.setCommType(2);
        redModel.setIRMapping(new RedModel.IRMappingBean(position, type, size));

        String str = ParseJsonUtils.getjsonStr(redModel);
        PhoneClient.getIntance().send(str);//发送设置消息
        // showWaitDialog("正在设置中...");
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

    private void showDengXin() {

        AlertDialog.Builder builder = new AlertDialog.Builder(aty);
        builder.setTitle("选择灯带信号");
        //    指定下拉列表的显示数据
        final String[] cities = {"RGB", "RBG", "GRB", "GBR", "BRG", "BGR"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mSignal = cities[which];
                mBinding.tvDengXin.setText(mSignal);
            }
        });
        builder.show();
    }

    private void showGanColor() {

        AlertDialog.Builder builder = new AlertDialog.Builder(aty);
        builder.setTitle("选择横梯的感应颜色");
        //    指定下拉列表的显示数据
        final String[] cities = {"单色", "七彩"};
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                mTransDirDetecColor = "单色".equals(cities[which]) ? "SolidColor" : "Colours";
                mBinding.tvGanColor.setText(cities[which]);
                setPickerState("SolidColor".equals(mTransDirDetecColor));
            }
        });
        builder.show();
    }
}
