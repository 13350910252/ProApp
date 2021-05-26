package com.yinp.tools.shap_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;

import androidx.annotation.IntDef;
import androidx.appcompat.widget.AppCompatTextView;

import com.yinp.tools.R;
import com.yinp.tools.utils.ToolsUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 *
 */
public class ShapeTextView extends AppCompatTextView {
    public ShapeTextView(Context context) {
        super(context);
        init(context, null);
    }

    public ShapeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private Drawable oneDrawable = null;
    private Drawable twoDrawable = null;
    private int state;
    private Context context;

    private int state_pressed = android.R.attr.state_pressed;
    private int state_selected = android.R.attr.state_selected;
    private int state_checked = android.R.attr.state_checked;
    private int state_focused = android.R.attr.state_focused;

    public static final int DEFAULT = 0;
    public static final int PRESSED = 1;
    public static final int SELECTED = 2;
    public static final int CHECKED = 3;
    public static final int FOCUSED = 4;

    @IntDef(value = {
            DEFAULT,
            PRESSED,
            SELECTED,
            CHECKED,
            FOCUSED
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface AndroidState {
    }


    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView);
        oneDrawable = ta.getDrawable(R.styleable.ShapeTextView_oneBg);
        twoDrawable = ta.getDrawable(R.styleable.ShapeTextView_twoBg);

        radius = ta.getInteger(R.styleable.ShapeTextView_radius, 0);
        leftTopRadius = ta.getInteger(R.styleable.ShapeTextView_lt_radius, 0);
        leftBottomRadius = ta.getInteger(R.styleable.ShapeTextView_lb_radius, 0);
        rightTopRadius = ta.getInteger(R.styleable.ShapeTextView_rt_radius, 0);
        rightBottomRadius = ta.getInteger(R.styleable.ShapeTextView_rb_radius, 0);
        state = ta.getInt(R.styleable.ShapeTextView_state, 0);


        ta.recycle();
        if (state != 0) {
            setOnClickListener(v -> {
            });
            isState = true;
        }
        if (radius != 0) {
            initShape(radius);
        } else {
            initShape(leftTopRadius, rightTopRadius, leftBottomRadius, rightBottomRadius);
        }
        fill(oneDrawable, twoDrawable);
    }

    private boolean isState;

    private void fill(Drawable oneD, Drawable twoD) {
        if (oneD != null && twoD != null) {
            if (oneD instanceof BitmapDrawable && twoD instanceof BitmapDrawable) {
                if (isState) {
                    initState(setBg((BitmapDrawable) oneD), setBg((BitmapDrawable) twoD));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg((BitmapDrawable) oneD));
                }
            } else if (oneD instanceof BitmapDrawable && twoD instanceof GradientDrawable) {
                if (isState) {
                    initState(setBg((BitmapDrawable) oneD), setBg((GradientDrawable) twoD));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg((BitmapDrawable) oneD));
                }
            } else if (oneD instanceof BitmapDrawable && twoD instanceof ColorDrawable) {
                if (isState) {
                    initState(setBg((BitmapDrawable) oneD), setBg(((ColorDrawable) twoD).getColor()));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg((BitmapDrawable) oneD));
                }
            } else if (twoD instanceof BitmapDrawable && oneD instanceof GradientDrawable) {
                if (isState) {
                    initState(setBg((BitmapDrawable) twoD), setBg((GradientDrawable) oneD));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg((BitmapDrawable) twoD));
                }
            } else if (twoD instanceof BitmapDrawable && oneD instanceof ColorDrawable) {
                if (isState) {
                    initState(setBg((BitmapDrawable) twoD), setBg(((ColorDrawable) oneD).getColor()));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg((BitmapDrawable) twoD));
                }
            } else if (oneD instanceof GradientDrawable && twoD instanceof GradientDrawable) {
                if (isState) {
                    initState(setBg((GradientDrawable) oneD), setBg((GradientDrawable) twoD));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg((GradientDrawable) oneD));
                }
            } else if (oneD instanceof GradientDrawable && twoD instanceof ColorDrawable) {
                if (isState) {
                    initState(setBg((GradientDrawable) oneD), setBg(((ColorDrawable) twoD).getColor()));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg((GradientDrawable) oneD));
                }
            } else if (twoD instanceof GradientDrawable && oneD instanceof ColorDrawable) {
                if (isState) {
                    initState(setBg((GradientDrawable) twoD), setBg(((ColorDrawable) oneD).getColor()));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg((GradientDrawable) twoD));
                }
            } else if (oneD instanceof ColorDrawable && twoD instanceof ColorDrawable) {
                if (isState) {
                    initState(setBg(((ColorDrawable) oneD).getColor()), setBg(((ColorDrawable) twoD).getColor()));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg(((ColorDrawable) oneD).getColor()));
                }
            }
        } else if (oneD != null) {//在一种颜色的情况下，自动设置75%不透明度的颜色
            if (oneD instanceof BitmapDrawable) {
                setMBackGround(setBg((BitmapDrawable) oneD));
            } else if (oneD instanceof GradientDrawable) {
                setMBackGround(setBg((GradientDrawable) oneD));
            } else {
                if (isState) {
                    String twoColor = getHexString(((ColorDrawable) oneD).getColor());
                    initState(setBg(((ColorDrawable) oneD).getColor()), setBg(Color.parseColor(twoColor)));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg(((ColorDrawable) oneD).getColor()));
                }
            }
        } else if (twoD != null) {
            if (twoD instanceof BitmapDrawable) {
                setMBackGround(setBg((BitmapDrawable) twoD));
            } else if (twoD instanceof GradientDrawable) {
                setMBackGround(setBg((GradientDrawable) twoD));
            } else {
                if (isState) {
                    String twoColor = getHexString(((ColorDrawable) twoD).getColor());
                    initState(setBg(((ColorDrawable) twoD).getColor()), setBg(Color.parseColor(twoColor)));
                    setMBackGround(stateListDrawable);
                } else {
                    setMBackGround(setBg(((ColorDrawable) twoD).getColor()));
                }
            }
        } else {
            setBackgroundColor(Color.RED);
        }
    }

    private void initState(Drawable drawable1, Drawable drawable2) {
        stateListDrawable = new StateListDrawable();
        switch (state) {
            case 1:
                stateListDrawable.addState(new int[]{-state_pressed}, drawable1);
                stateListDrawable.addState(new int[]{state_pressed}, drawable2);
                break;
            case 2:
                stateListDrawable.addState(new int[]{-state_selected}, drawable1);
                stateListDrawable.addState(new int[]{state_selected}, drawable2);
                break;
            case 3:
                stateListDrawable.addState(new int[]{-state_checked}, drawable1);
                stateListDrawable.addState(new int[]{state_checked}, drawable2);
                break;
            case 4:
                stateListDrawable.addState(new int[]{-state_focused}, drawable1);
                stateListDrawable.addState(new int[]{state_focused}, drawable2);
                break;
        }
    }

    private StateListDrawable stateListDrawable;
    private RoundRectShape roundRectShape;

    private int radius, leftBottomRadius, leftTopRadius, rightTopRadius, rightBottomRadius;
    private int color;
    private Drawable drawable;


    public void setRadius(int radius) {
        this.radius = radius;
        initShape(radius);
    }

    public void setRadius(int leftBottomRadius, int leftTopRadius, int rightTopRadius, int rightBottomRadius) {
        this.leftBottomRadius = leftBottomRadius;
        this.leftTopRadius = leftTopRadius;
        this.rightTopRadius = rightTopRadius;
        this.rightBottomRadius = rightBottomRadius;
        initShape(leftBottomRadius, leftTopRadius, rightTopRadius, rightBottomRadius);
    }

    /**
     * 动态设置,圆角必须设置在图片和颜色之前
     *
     * @param color
     */
    public void setColor(int color) {
        setColor(color, DEFAULT);
    }

    public void setColor(int color, @AndroidState int state) {
        this.color = color;
        this.state = state;
        if (state != 0) {
            setOnClickListener(v -> {
            });
            isState = true;
        }
        if (isState) {
            String twoColor = getHexString(color);
            initState(setBg(color), setBg(Color.parseColor(twoColor)));
            setMBackGround(stateListDrawable);
        } else {
            setMBackGround(setBg(color));
        }
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        if (drawable instanceof BitmapDrawable) {
            setMBackGround(setBg((BitmapDrawable) drawable));
        } else if (drawable instanceof GradientDrawable) {
            setMBackGround(setBg((GradientDrawable) drawable));
        } else if (drawable instanceof ColorDrawable) {
            setMBackGround(setBg(((ColorDrawable) drawable).getColor()));
        }
    }

    /**
     * 所有圆角一样
     *
     * @param radius
     */
    private void initShape(int radius) {
        if (radius != 0) {
            radius = (int) ToolsUtils.dpToPx(context, radius);
            float[] outerRadii = {radius, radius, radius, radius, radius, radius, radius, radius};
            roundRectShape = new RoundRectShape(outerRadii, null, null);
        }
    }

    /**
     * 单独设置圆角
     *
     * @param leftBottomRadius
     * @param leftTopRadius
     * @param rightTopRadius
     * @param rightBottomRadius
     */
    private void initShape(int leftTopRadius, int rightTopRadius, int leftBottomRadius, int rightBottomRadius) {
        if (leftBottomRadius != 0 || leftTopRadius != 0 || rightTopRadius != 0 || rightBottomRadius != 0) {
            leftBottomRadius = (int) ToolsUtils.dpToPx(context, leftBottomRadius);
            leftTopRadius = (int) ToolsUtils.dpToPx(context, leftTopRadius);
            rightTopRadius = (int) ToolsUtils.dpToPx(context, rightTopRadius);
            rightBottomRadius = (int) ToolsUtils.dpToPx(context, rightBottomRadius);
            float[] outerRadii = {leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
            roundRectShape = new RoundRectShape(outerRadii, null, null);
        }
    }

    /**
     * 颜色
     */
    public void setColorBg() {
        setBg(color);
    }

    private Drawable setBg(int color) {
        ShapeDrawable shapeDrawable;
        if (roundRectShape == null) {
            shapeDrawable = new ShapeDrawable();
        } else {
            shapeDrawable = new ShapeDrawable(roundRectShape);
        }
        shapeDrawable.getPaint().setColor(color);
        setMBackGround(shapeDrawable);
        return shapeDrawable;

    }

    /**
     * xml写的gradientDrawable
     */
    public void setGradientBg() {
        setBg((GradientDrawable) oneDrawable);
    }

    private Drawable setBg(GradientDrawable drawable) {
        if (drawable == null) {
            return null;
        }
        setMBackGround(drawable);
        return drawable;
    }

    /**
     * 设置的图片
     */
    public void setBitmapBg() {
        setBg((BitmapDrawable) oneDrawable);
    }

    private Drawable setBg(BitmapDrawable drawable) {
        if (drawable == null) {
            return null;
        }
        BitmapShader bitmapShader = new BitmapShader(drawable.getBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        ShapeDrawable shapeDrawable;
        if (roundRectShape == null) {
            shapeDrawable = new ShapeDrawable();
        } else {
            shapeDrawable = new ShapeDrawable(roundRectShape);
        }
        shapeDrawable.getPaint().setShader(bitmapShader);
        setMBackGround(shapeDrawable);
        return shapeDrawable;
    }

    private void setMBackGround(Drawable drawable) {
        setBackground(drawable);
    }

    /**
     * 默认改变75%的透明度
     *
     * @param color
     * @return
     */
    private String getHexString(int color) {
        String s = "#";
        int colorStr = (color & 0xbf000000) | (color & 0x00ff0000) | (color & 0x0000ff00) | (color & 0x000000ff);
        s = s + Integer.toHexString(colorStr);
        return s;
    }
}
