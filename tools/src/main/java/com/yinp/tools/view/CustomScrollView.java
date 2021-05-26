package com.yinp.tools.view;

/**
 * @author yinp.
 * @title
 * @description
 * @date 2019/7/29,9:13.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.yinp.tools.R;
import com.yinp.tools.utils.ToolsUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CustomScrollView extends View {


    private Context mContext;

    private Paint mPaint;
    private int mLightColor, mDarkColor;
    private float mHalfWidth, mHalfHeight, mQuarterHeight;
    private float maxTextSize, minTextSize, mTextSizeRange;
    private float mTextSpacing, mHalfTextSpacing;

    private float mScrollDistance;
    private float mLastTouchY;
    private List<String> mDataList = new ArrayList<>();
    private int mSelectedIndex;
    private boolean mCanScroll = true;//是否能够滚动
    private OnSelectListener mOnSelectListener;

    private Timer mTimer = new Timer();
    private TimerTask mTimerTask;
    private Handler mHandler = new ScrollHandler(this);

    /**
     * 自动回滚到中间的速度
     */
    private static final float AUTO_SCROLL_SPEED = 10;

    /**
     * 透明度：最小 120，最大 255，极差 135
     */
    private static final int TEXT_ALPHA_MIN = 120;
    private static final int TEXT_ALPHA_RANGE = 135;

    public CustomScrollView(Context context) {
        super(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint(attrs);
    }

    public CustomScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint(attrs);
    }

    /**
     * 选择结果回调接口
     */
    public interface OnSelectListener {
        void onSelect(View view, String selected, int position);
    }

    private static class ScrollTimerTask extends TimerTask {
        private WeakReference<Handler> mWeakHandler;

        private ScrollTimerTask(Handler handler) {
            mWeakHandler = new WeakReference<>(handler);
        }

        @Override
        public void run() {
            Handler handler = mWeakHandler.get();
            if (handler == null) return;

            handler.sendEmptyMessage(0);
        }
    }

    private static class ScrollHandler extends Handler {
        private WeakReference<CustomScrollView> mWeakView;

        private ScrollHandler(CustomScrollView view) {
            mWeakView = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            CustomScrollView view = mWeakView.get();
            if (view == null) return;

            view.keepScrolling();
        }
    }

    private Drawable selectedColor, unSelectedColor;

    private void initPaint(AttributeSet attrs) {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.CustomScrollView);

        maxTextSize = ta.getInteger(R.styleable.CustomScrollView_textMaxSize, 18);
        minTextSize = ta.getInteger(R.styleable.CustomScrollView_textMinSize, 14);

        maxTextSize = ToolsUtils.dpToPx(mContext, maxTextSize);
        minTextSize = ToolsUtils.dpToPx(mContext, minTextSize);
        selectedColor = ta.getDrawable(R.styleable.CustomScrollView_selectedColor);
        unSelectedColor = ta.getDrawable(R.styleable.CustomScrollView_unSelectedColor);

        ta.recycle();
        if (selectedColor instanceof ColorDrawable && unSelectedColor instanceof ColorDrawable) {
            mLightColor = ((ColorDrawable) selectedColor).getColor();//被选中的颜色
            mDarkColor = ((ColorDrawable) unSelectedColor).getColor();
        } else {
            mLightColor = ContextCompat.getColor(mContext, R.color.blue);//被选中的颜色
            mDarkColor = ContextCompat.getColor(mContext, R.color.black99);
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mHalfWidth = getMeasuredWidth() / 2f;
        int height = getMeasuredHeight();
        mHalfHeight = height / 2f;
        mQuarterHeight = height / 4f;
        mTextSizeRange = maxTextSize - minTextSize;//字体大小变化

        mTextSpacing = minTextSize * 2.8f;
        mHalfTextSpacing = mTextSpacing / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mSelectedIndex >= mDataList.size()) return;

        // 绘制选中的 text
        drawText(canvas, mLightColor, mScrollDistance, mDataList.get(mSelectedIndex));

        // 绘制选中上方的 text
        for (int i = 1; i <= mSelectedIndex; i++) {
            drawText(canvas, mDarkColor, mScrollDistance - i * mTextSpacing,
                    mDataList.get(mSelectedIndex - i));
        }

        // 绘制选中下方的 text
        int size = mDataList.size() - mSelectedIndex;
        for (int i = 1; i < size; i++) {
            drawText(canvas, mDarkColor, mScrollDistance + i * mTextSpacing,
                    mDataList.get(mSelectedIndex + i));
        }
    }

    private void drawText(Canvas canvas, int textColor, float offsetY, String text) {
        if (TextUtils.isEmpty(text)) return;

        float scale = 1 - (float) Math.pow(offsetY / mQuarterHeight, 2);
        scale = scale < 0 ? 0 : scale;
        mPaint.setTextSize(minTextSize + mTextSizeRange * scale);
        mPaint.setColor(textColor);
        mPaint.setAlpha(TEXT_ALPHA_MIN + (int) (TEXT_ALPHA_RANGE * scale));

        // text 居中绘制，mHalfHeight + offsetY 是 text 的中心坐标
        Paint.FontMetrics fm = mPaint.getFontMetrics();
        float baseline = mHalfHeight + offsetY - (fm.top + fm.bottom) / 2f;
        canvas.drawText(text, mHalfWidth, baseline, mPaint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return mCanScroll && super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                cancelTimerTask();
                mLastTouchY = event.getY();//当期触点的Y坐标
                break;
            //移动的时候
            case MotionEvent.ACTION_MOVE:
                float offsetY = event.getY();//滑动后当期触摸点
                mScrollDistance += offsetY - mLastTouchY;//意味着滑动最大距离是最开始触点的y坐标值
                if (mScrollDistance > mHalfTextSpacing) {
                    if (mSelectedIndex == 0) {
                        mLastTouchY = offsetY;
                        invalidate();
                        break;
                    } else {
                        mSelectedIndex--;
                    }
                    mScrollDistance -= mTextSpacing;
                } else if (mScrollDistance < -mHalfTextSpacing) {

                    if (mSelectedIndex == mDataList.size() - 1) {
                        mLastTouchY = offsetY;
                        invalidate();
                        break;
                    } else {
                        mSelectedIndex++;
                    }

                    mScrollDistance += mTextSpacing;
                }
                mLastTouchY = offsetY;
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起手后 mSelectedIndex 由当前位置滚动到中间选中位置
                if (Math.abs(mScrollDistance) < 0.01) {
                    mScrollDistance = 0;
                    break;
                }
                cancelTimerTask();
                mTimerTask = new ScrollTimerTask(mHandler);
                mTimer.schedule(mTimerTask, 0, 10);
                break;
        }
        return true;
    }

    private void cancelTimerTask() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (mTimer != null) {
            mTimer.purge();
        }
    }

    private void keepScrolling() {
        if (Math.abs(mScrollDistance) < AUTO_SCROLL_SPEED) {
            mScrollDistance = 0;
            if (mTimerTask != null) {
                cancelTimerTask();

                if (mOnSelectListener != null && mSelectedIndex < mDataList.size()) {
                    mOnSelectListener.onSelect(this, mDataList.get(mSelectedIndex), mSelectedIndex);
                }
            }
        } else if (mScrollDistance > 0) {
            // 向下滚动
            mScrollDistance -= AUTO_SCROLL_SPEED;
        } else {
            // 向上滚动
            mScrollDistance += AUTO_SCROLL_SPEED;
        }
        invalidate();
    }

    /**
     * 设置数据
     */
    public void setDataList(List<String> list) {
        if (list == null || list.isEmpty()) return;

        mDataList = list;
        // 重置 mSelectedIndex，防止溢出
        mSelectedIndex = 0;
        invalidate();
    }

    /**
     * 选择选中项
     */
    public void setSelected(int index) {
        if (index >= mDataList.size()) return;

        mSelectedIndex = index;
        invalidate();
    }

    /**
     * 设置是否可以滚动
     */
    public void setmCanScroll(boolean canScroll) {
        mCanScroll = canScroll;
    }

    /**
     * 设置选择结果监听
     */
    public void setOnSelectListener(OnSelectListener listener) {
        mOnSelectListener = listener;
    }

    /**
     * 销毁资源
     */
    public void onDestroy() {
        mOnSelectListener = null;
        mHandler.removeCallbacksAndMessages(null);
        cancelTimerTask();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

}
