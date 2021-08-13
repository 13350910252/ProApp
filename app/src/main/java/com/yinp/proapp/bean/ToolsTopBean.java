package com.yinp.proapp.bean;

/**
 * @author :yinpeng
 * date      :2021/8/13
 * package   :com.yinp.proapp.bean
 * describe  :
 */
public class ToolsTopBean {
    private String title;

    public ToolsTopBean(String title, int drawableId) {
        this.title = title;
        this.drawableId = drawableId;
    }

    private int drawableId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }
}
