package com.yinp.proapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.yinp.proapp.R;
import com.yinp.proapp.utils.AppUtils;

public class TriangleLinearLayout extends LinearLayout {
    public TriangleLinearLayout(Context context) {
        this(context, null);
    }

    public TriangleLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    Paint paint1;//画类容
    Paint paint2;//画三角形
    Path path;

    private int triangleWidth, triangleHeight;//三角形的宽高
    private int contentColor, triangleColor;
    private int contentCorners;//圆角
    private int trianglePosition;//三角形所处位置
    private int distance;//跟着三角形位置来的

    private boolean isFirst = false;//只加载一次避免多次重复加载

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TriangleLinearLayout);

        triangleWidth = ta.getInteger(R.styleable.TriangleLinearLayout_triangle_width, 8);
        triangleHeight = ta.getInteger(R.styleable.TriangleLinearLayout_triangle_height, 8);
        triangleColor = ta.getColor(R.styleable.TriangleLinearLayout_triangle_color, 0);
        trianglePosition = ta.getInteger(R.styleable.TriangleLinearLayout_triangle_position, 1);
        distance = ta.getInteger(R.styleable.TriangleLinearLayout_distance, 0);


        contentColor = ta.getColor(R.styleable.TriangleLinearLayout_content_color, 0);
        contentCorners = ta.getInteger(R.styleable.TriangleLinearLayout_content_corners, 0);

        ta.recycle();

        triangleWidth = (int) AppUtils.dpToPx(context, triangleWidth);
        triangleHeight = (int) AppUtils.dpToPx(context, triangleHeight);
        contentCorners = (int) AppUtils.dpToPx(context, contentCorners);
        distance = (int) AppUtils.dpToPx(context, distance);
        if (triangleColor == 0) {
            triangleColor = contentColor;
        }
        if (contentColor == 0) {
            contentColor = triangleColor;
        }
        setPadding(0, triangleHeight, 0, 0);
        setWillNotDraw(false);
        setBackgroundResource(R.color.transparent);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isFirst) {
            init2();
            isFirst = true;
        }
    }

    private void init2() {
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint1.setColor(contentColor);
        paint1.setStyle(Paint.Style.FILL);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint2.setColor(triangleColor);
        paint2.setStyle(Paint.Style.FILL);

        path = new Path();
        switch (trianglePosition) {
            case 1:
                editX(true);
                break;
            case 2:
                editX(false);
                break;
        }
    }

    //    在x轴上变化
    private void editX(boolean plus) {
        if (plus) {
            path.moveTo(distance, triangleHeight);
            path.lineTo(distance + triangleWidth / 2, 0);
            path.lineTo(distance + triangleWidth, triangleHeight);
            path.close();
        } else {
            path.moveTo(getWidth() - distance, triangleHeight);
            path.lineTo(getWidth() - distance - triangleWidth / 2, 0);
            path.lineTo(getWidth() - distance - triangleWidth, triangleHeight);
            path.close();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint2);
        canvas.drawRoundRect(0, triangleHeight, getWidth(), getHeight(), contentCorners, contentCorners, paint2);
    }
}
