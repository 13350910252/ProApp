package com.yinp.proapp.module.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.yinp.proapp.R;

public class ClipCircleView extends AppCompatImageView {

    public ClipCircleView(Context context) {
        super(context);
        init();
    }

    public ClipCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Paint topLayerPaint;
    Path clipPath;

    Bitmap bottomBitmap;
    float radius = 240;
    float circlePointX;
    float circlePointY;

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        topLayerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        topLayerPaint.setDither(true);

        clipPath = new Path();

    }

    private boolean isFirst = true;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isFirst) {
            isFirst = false;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.outWidth = getWidth();
            options.outHeight = getHeight();
            bottomBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.picture, options);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clipPath.reset();
        clipPath.addCircle(circlePointX, circlePointY, radius, Path.Direction.CCW);
        canvas.clipPath(clipPath);
        canvas.drawBitmap(bottomBitmap, 0, 0, topLayerPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                circlePointX = getX();
                circlePointY = getY();
                break;
            case MotionEvent.ACTION_MOVE:
                circlePointX = event.getX();
                circlePointY = event.getY();
                break;
            default:
        }
        invalidate();
        return true;
    }
}
