package com.mf.lightcontrol.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.mf.lightcontrol.R;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by LiMing
 * Date 2018/12/20
 */
public class ColorPickerView extends CircleImageView {
    Context context;
    private Bitmap iconBitMap;
    float iconRadius;// 吸管圆的半径
    float iconCenterX;
    float iconCenterY;
    PointF iconPoint;// 点击位置坐标

    public ColorPickerView(Context context) {
        this(context, null);
    }

    public ColorPickerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    Paint mBitmapPaint;
    Bitmap imageBitmap;
    float viewRadius;// 整个view半径
    float radius;// 图片半径

    /**
     * 初始化画笔
     */
    private void init() {
        iconBitMap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.seelct_yuan_icon);// 吸管的图片
        iconRadius = iconBitMap.getWidth() / 2;// 吸管的图片一半

        mBitmapPaint = new Paint();
        iconPoint = new PointF();

        imageBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        radius = imageBitmap.getHeight() / 2;// 图片半径

        // // 初始化
        iconPoint.x = radius / 5;
        iconPoint.y = radius;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    Canvas canvas;

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        this.canvas = canvas;

        viewRadius = this.getWidth() / 2;// 整个view半径

        canvas.drawBitmap(iconBitMap, iconPoint.x - iconRadius, iconPoint.y
                - iconRadius, mBitmapPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int pixel;
        int r;
        int g;
        int b;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                proofLeft(x, y);
                pixel = getImagePixel(iconPoint.x, iconPoint.y);
                r = Color.red(pixel);
                g = Color.green(pixel);
                b = Color.blue(pixel);
                if (mChangedListener != null) {
                    mChangedListener.onMoveColor(r, g, b);
                }
                if (isMove) {
                    isMove = !isMove;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                pixel = getImagePixel(iconPoint.x, iconPoint.y);
                r = Color.red(pixel);
                g = Color.green(pixel);
                b = Color.blue(pixel);
                if (mChangedListener != null) {
                    mChangedListener.onColorChanged(r, g, b);
                }
                break;

            default:
                break;
        }
        return true;
    }


    public void getColor() {
        int pixel;
        int r;
        int g;
        int b;
        pixel = getImagePixel(iconPoint.x, iconPoint.y);
        r = Color.red(pixel);
        g = Color.green(pixel);
        b = Color.blue(pixel);
        if (mChangedListener != null) {
            mChangedListener.onColorChanged(r, g, b);
        }
    }

    public int getImagePixel(float x, float y) {

        Bitmap bitmap = imageBitmap;
        // 为了防止越界
        int intX = (int) x;
        int intY = (int) y;
        if (intX < 0)
            intX = 0;
        if (intY < 0)
            intY = 0;
        if (intX >= bitmap.getWidth()) {
            intX = bitmap.getWidth() - 1;
        }
        if (intY >= bitmap.getHeight()) {
            intY = bitmap.getHeight() - 1;
        }
        int pixel = bitmap.getPixel(intX, intY);

        return pixel;

    }

    /**
     * 定位到指定颜色位置
     *
     * @param color_str
     */
    public void toColorPoint(String color_str) {
        int color_old = Color.parseColor(color_str);
        Bitmap bitmap = imageBitmap;

        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                int color = bitmap.getPixel(i, j);
                // Calculate gray value (RGB -> YUV)
                if (color_old == color) {
                    iconPoint.x = i;
                    iconPoint.y = j;
                    invalidate();
                    break;
                }
            }
        }
    }


    /**
     * R = sqrt(x * x + y * y)
     * point.x = x * r / R + r
     * point.y = y * r / R + r
     */
    private void proofLeft(float x, float y) {

        float h = x - viewRadius; // 取xy点和圆点 的三角形宽
        float w = y - viewRadius;// 取xy点和圆点 的三角形长
        float h2 = h * h;
        float w2 = w * w;
        float distance = (float) Math.sqrt((h2 + w2)); // 勾股定理求 斜边距离
        if (distance > radius) { // 如果斜边距离大于半径，则取点和圆最近的一个点为x,y
            float maxX = x - viewRadius;
            float maxY = y - viewRadius;
            x = ((radius * maxX) / distance) + viewRadius; // 通过三角形一边平行原理求出x,y
            y = ((radius * maxY) / distance) + viewRadius;
        }
       /* float percentWidthSize = AutoUtils.getPercentWidthSize(200);
        if (h<0) {
            h=-h;
        }
        if (w<0) {
            w=-w;
        }
        if (h<=percentWidthSize||w<=percentWidthSize) {
            return;
        }*/
        iconPoint.x = x;
        iconPoint.y = y;

        isMove = true;
    }

    boolean isMove;

    public void setOnColorChangedListenner(OnColorChangedListener l) {
        this.mChangedListener = l;
    }

    private OnColorChangedListener mChangedListener;

    // 内部接口 回调颜色 rgb值
    public interface OnColorChangedListener {
        // 手指抬起，确定颜色回调
        void onColorChanged(int r, int g, int b);

        // 移动时颜色回调
        void onMoveColor(int r, int g, int b);
    }
}
