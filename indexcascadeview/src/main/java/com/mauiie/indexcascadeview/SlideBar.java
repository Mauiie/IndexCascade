package com.mauiie.indexcascadeview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TaiJL on 2016/2/16.
 */
public class SlideBar extends View {
    public SlideBar(Context context) {
        super(context);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint = new Paint();
    private OnTouchLetterChangeListenner listenner;
    // 是否画出背景
    private boolean showBg = false;
    // 选中的项
    private int choose = -1;

    public static String[] letters = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#"};


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取宽和高
        int width = getWidth();
        int height = getHeight();
        // 每个字母的高度
        int singleHeight = height / letters.length;
//        if (showBg) {
//            画出背景
//            canvas.drawColor(Color.parseColor("#55000000"));
//        }
        // 画字母
        for (int i = 0; i < letters.length; i++) {
            paint.setColor(Color.parseColor("#AAAAAA"));
            // 设置字体格式
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(dp2Px(10));
            // 如果这一项被选中，则换一种颜色画
            if (i == choose) {
                paint.setColor(Color.parseColor("#38ACFF"));
                paint.setFakeBoldText(true);
            }
            // 要画的字母的x,y坐标
            float posX = width / 2 - paint.measureText(letters[i]) / 2;
            float posY = i * singleHeight + singleHeight;
            // 画出字母
            canvas.drawText(letters[i], posX, posY, paint);
            // 重新设置画笔
            paint.reset();
        }
    }

    private float dp2Px(int dp) {
        Context context = getContext();
        if (null != context) {
            float density = context.getResources().getDisplayMetrics().density;
            return dp * density + 0.5f;
        }
        return dp;
    }

    /**
     * 处理SlideBar的状态
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        // 算出点击的字母的索引
        final int index = (int) (y / getHeight() * letters.length);
        // 保存上次点击的字母的索引到oldChoose
        final int oldChoose = choose;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if (oldChoose != index && listenner != null && index >= 0
                        && index < letters.length) {
                    choose = index;
                    listenner.onTouchLetterChange(showBg, letters[index]);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (oldChoose != index && listenner != null && index >= 0
                        && index < letters.length) {
                    choose = index;
                    listenner.onTouchLetterChange(showBg, letters[index]);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                showBg = false;
                choose = -1;
                if (listenner != null) {
                    if (index <= 0) {
                        listenner.onTouchLetterChange(showBg, "A");
                    } else if (index > 0 && index < letters.length) {
                        listenner.onTouchLetterChange(showBg, letters[index]);
                    } else if (index >= letters.length) {
                        listenner.onTouchLetterChange(showBg, "Z");
                    }
                }
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 回调方法，注册监听器
     */
    public void setOnTouchLetterChangeListenner(
            OnTouchLetterChangeListenner listenner) {
        this.listenner = listenner;
    }

    /**
     * SlideBar 的监听器接口
     */
    public interface OnTouchLetterChangeListenner {

        void onTouchLetterChange(boolean isTouched, String s);
    }

}

