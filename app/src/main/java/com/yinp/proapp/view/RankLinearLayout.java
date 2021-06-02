package com.yinp.proapp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import com.yinp.proapp.R;

public class RankLinearLayout extends LinearLayout {
    public RankLinearLayout(Context context) {
        super(context);
    }

    public RankLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBase(context, attrs);
    }

    public RankLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBase(context, attrs);
    }

    private Paint paint;
    private float percent = 0f;
    private float percentWidth;
    private int duration;
    private int bgColor;
    private boolean isStart;

    private void initBase(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RankLinearLayout);
        bgColor = array.getColor(R.styleable.RankLinearLayout_bgColor, 0);
        percentWidth = array.getFloat(R.styleable.RankLinearLayout_schedule, 0);
        duration = array.getInteger(R.styleable.RankLinearLayout_duration, 600);
        isStart = array.getBoolean(R.styleable.RankLinearLayout_isStart, false);
        array.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.FILL);
        setWillNotDraw(false);
        if (isStart) {
            setAnimator();
        }
    }

    public RankLinearLayout setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public RankLinearLayout setBgColor(@IdRes int bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public RankLinearLayout setPercentWidth(float percentWidth) {
        this.percentWidth = percentWidth;
        return this;
    }

    public void start() {
        setAnimator();
    }

    private void setAnimator() {
        if (percentWidth == 0) {
            return;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, percentWidth);
        valueAnimator.addUpdateListener(animation -> {
            percent = (float) animation.getAnimatedValue();
            postInvalidate();
        });
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getRight() * percent, getMeasuredHeight(), paint);
    }
}
