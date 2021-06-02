package com.yinp.proapp.module.wanandroid.bean;

import java.util.List;

public class WanSysListBean2 {
    public WanSysListBean2(int courseId, int id, String name, int order, int parentChapterId, boolean userControlSetTop, int visible, boolean isTitle) {
        this.courseId = courseId;
        this.id = id;
        this.name = name;
        this.order = order;
        this.parentChapterId = parentChapterId;
        this.userControlSetTop = userControlSetTop;
        this.visible = visible;
        this.isTitle = isTitle;
    }

    /**
     * children : [{"children":[],"courseId":13,"id":60,"name":"Android Studio相关","order":1000,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":169,"name":"gradle","order":1001,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":269,"name":"官方发布","order":1002,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":529,"name":"90-120hz","order":1003,"parentChapterId":150,"userControlSetTop":false,"visible":1}]
     * courseId : 13
     * id : 150
     * name : 开发环境
     * order : 1
     * parentChapterId : 0
     * userControlSetTop : false
     * visible : 1
     */

    private int courseId;
    private int id;
    private String name;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;
    private boolean isTitle;//判断是否是标签

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    /**
     * children : []
     * courseId : 13
     * id : 60
     * name : Android Studio相关
     * order : 1000
     * parentChapterId : 150
     * userControlSetTop : false
     * visible : 1
     */
    private List<?> children;

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }
}
