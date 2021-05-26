package com.yinp.tools.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yinp.tools.R;
import com.yinp.tools.utils.ToolsUtils;

/**
 * @author yinp.
 * @title
 * @description
 * @date 2019/9/16,10:23.
 */
//继承的layout可以随时更改如LinearLayout
//测试中的一些圆角和背景都能通过相应的办法解决
public class ShadowFrameLayout extends FrameLayout {
    public ShadowFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public ShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShadowFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private boolean isPaddingLeft;
    private boolean isPaddingTop;
    private boolean isPaddingRight;
    private boolean isPaddingBottom;
    private boolean isWidgetBorder;

    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;
    private int mShadowColor;
    private int mBackgroundColor;

    private float mShadowLimit;
    private float mCornerRadius;

    private Paint shadowPaint;
    private Paint bgPaint;

    //初始化各个参数和画笔
    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ShadowFrameLayout);
        isPaddingLeft = array.getBoolean(R.styleable.ShadowFrameLayout_sl_padding_left, true);
        isPaddingTop = array.getBoolean(R.styleable.ShadowFrameLayout_sl_padding_top, true);
        isPaddingRight = array.getBoolean(R.styleable.ShadowFrameLayout_sl_padding_right, true);
        isPaddingBottom = array.getBoolean(R.styleable.ShadowFrameLayout_sl_padding_bottom, true);

        mShadowColor = array.getColor(R.styleable.ShadowFrameLayout_sl_shadow_color,
                getResources().getColor(R.color.shadow_color));//必须要一个透明度
        mBackgroundColor = array.getColor(R.styleable.ShadowFrameLayout_sl_background_color,
                getResources().getColor(R.color.white));

        mShadowLimit = array.getDimension(R.styleable.ShadowFrameLayout_sl_shadow_width, 4);
        mCornerRadius = array.getDimension(R.styleable.ShadowFrameLayout_sl_corner_radius, 0);
        isWidgetBorder = array.getBoolean(R.styleable.ShadowFrameLayout_sl_widget_border, true);
        array.recycle();

        mShadowLimit = ToolsUtils.dpToPx(context, mShadowLimit);
        mCornerRadius = ToolsUtils.dpToPx(context, mCornerRadius);//决定边角的圆角

        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setStyle(Paint.Style.FILL);

        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(mBackgroundColor);
        setPad();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setBackgroundCompat(w, h);
        }
    }

    private void setBackgroundCompat(int w, int h) {
        Bitmap bitmap = createBitmap(w, h);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        setBackground(drawable);
    }

    private Bitmap createBitmap(int shadowWidth, int shadowHeight) {
        Bitmap bitmap = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        RectF shadowRectF = new RectF(
                mShadowLimit,
                mShadowLimit,
                shadowWidth - mShadowLimit,
                shadowHeight - mShadowLimit
        );
        shadowPaint.setColor(Color.TRANSPARENT);//设置透明
        if (!isInEditMode()) {
            //四个参数：radius,阴影的扩散范围，为0就没有，越大越模糊；阴影平面dx和dy上下偏移量，阴影颜色
            shadowPaint.setShadowLayer(mShadowLimit, 0, 0, mShadowColor);
        }
        canvas.drawRoundRect(shadowRectF, mCornerRadius, mCornerRadius, shadowPaint);
        return bitmap;
    }

    //设置边距来显示阴影
    private void setPad() {
        if (isPaddingLeft) {
            paddingLeft = (int) mShadowLimit;
        } else {
            paddingLeft = 0;
        }
        if (isPaddingTop) {
            paddingTop = (int) mShadowLimit;
        } else {
            paddingTop = 0;
        }
        if (isPaddingRight) {
            paddingRight = (int) mShadowLimit;
        } else {
            paddingRight = 0;
        }
        if (isPaddingBottom) {
            paddingBottom = (int) mShadowLimit;
        } else {
            paddingBottom = 0;
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    private RectF backgroundRectF = new RectF();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isWidgetBorder) {
            backgroundRectF.left = paddingLeft;
            backgroundRectF.top = paddingTop;
            backgroundRectF.right = getWidth() - paddingRight;
            backgroundRectF.bottom = getHeight() - paddingBottom;
            int trueHeight = (int) (backgroundRectF.bottom - backgroundRectF.top);
            if (mCornerRadius > trueHeight / 2) {
                //画圆角矩阵
                canvas.drawRoundRect(backgroundRectF, trueHeight / 2, trueHeight / 2, bgPaint);
            } else {
                canvas.drawRoundRect(backgroundRectF, mCornerRadius, mCornerRadius, bgPaint);
            }
        }
    }
}
