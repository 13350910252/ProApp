package com.yinp.proapp.module.wanandroid.bean;

import java.util.List;

public class CollectionListBean {
    /**
     * curPage : 1
     * datas : [{"author":"xiaoyang","chapterId":440,"chapterName":"官方","courseId":13,"desc":"<p>在我们的印象里，如果构造一个 Dialog 传入一个非 Activiy 的 context，则可能会出现 bad token exception。<\/p>\r\n<p>今天我们就来彻底搞清楚这一块，问题来了：<\/p>\r\n<ol>\r\n<li>为什么传入一个非 Activity 的 context 会出现错误？<\/li>\r\n<li>传入的 context 一定要是 Activity 吗？<\/li>\r\n<\/ol>","envelopePic":"","id":199721,"link":"https://www.wanandroid.com/wenda/show/18281","niceDate":"2021-06-15 09:06","origin":"","originId":18281,"publishTime":1623719193000,"title":"每日一问 | Dialog 的构造方法的 context 必须传入 Activity吗？","userId":97982,"visible":0,"zan":0},{"author":"xiaoyang","chapterId":440,"chapterName":"官方","courseId":13,"desc":"<p>最近一直在补一些 C/C++的知识，导致 Android 相关知识看的少了，导致每日一问憋半天憋不出来。<\/p>\r\n<p>还好，我又更新了...<\/p>\r\n<p>之前公众号推文的时候，推送到混淆的时候，经常会说到「脱糖」，也有同学在留言区问什么是脱糖呀？<\/p>\r\n<p>今天的问题就是为了带大家彻底搞清楚什么是脱糖：<\/p>\r\n<ol>\r\n<li>脱糖产生的原因是什么？<\/li>\r\n<li>脱糖在 D8 产生后发生了什么变化？<\/li>\r\n<li>脱糖我们需要关注吗？在我们做什么事情的时候可能会影响到呢？<\/li>\r\n<\/ol>\r\n<p>求解答~<\/p>","envelopePic":"","id":199720,"link":"https://www.wanandroid.com/wenda/show/18615","niceDate":"2021-06-15 09:05","origin":"","originId":18615,"publishTime":1623719151000,"title":"每日一问 | 我们经常说到的 Android 脱糖指的是什么？","userId":97982,"visible":0,"zan":0},{"author":"xiaoyang","chapterId":440,"chapterName":"官方","courseId":13,"desc":"<p>最近聊到一种编译优化的方案，号称秒级别：<\/p>\r\n<ol>\r\n<li>每次拿到修改 java 文件，编译为 class；<\/li>\r\n<li>将 1 中的 class 转成 dex；<\/li>\r\n<li>push 到 sdcard，然后重启生效；<\/li>\r\n<\/ol>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>第一步中将特定的 Java 文件转成 class 如何操作？如何极快的操作？<\/li>\r\n<li>如果是 kotlin 文件呢？<\/li>\r\n<\/ol>","envelopePic":"","id":196047,"link":"https://www.wanandroid.com/wenda/show/18453","niceDate":"2021-06-01 15:50","origin":"","originId":18453,"publishTime":1622533857000,"title":"每日一问 | 极致的编译优化如何实现？","userId":97982,"visible":0,"zan":0},{"author":"xiaoyang","chapterId":440,"chapterName":"官方","courseId":13,"desc":"<ol>\r\n<li>你有任何想问的问题<\/li>\r\n<li>任何方向<\/li>\r\n<li>关键词<\/li>\r\n<\/ol>\r\n<p>都可以直接留言提出。<\/p>","envelopePic":"","id":195847,"link":"https://www.wanandroid.com/wenda/show/8857","niceDate":"2021-05-31 17:33","origin":"","originId":8857,"publishTime":1622453633000,"title":"每日一问 问答征集","userId":97982,"visible":0,"zan":0},{"author":"xiaoyang","chapterId":440,"chapterName":"官方","courseId":13,"desc":"<p>之前看文章，经常看到一些分析 Dialog、PopupWindow的文章，有些文章分析如下：<\/p>\r\n<p><strong>Dialog有自己独立的Window，而PopupWindow没有，所以PopupWindow可以称之为子窗口，而 Dialog不是。<\/strong><\/p>\r\n<p>问题来了：<\/p>\r\n<ol>\r\n<li>这种说法合理吗？<\/li>\r\n<li>在Android中，有没有子窗口的概念呢？如果有到底应该以什么为标准呢？<\/li>\r\n<\/ol>","envelopePic":"","id":193893,"link":"https://wanandroid.com/wenda/show/18192","niceDate":"2021-05-24 09:41","origin":"","originId":18192,"publishTime":1621820509000,"title":"每日一问 | Android中的子窗口到底指的是什么？","userId":97982,"visible":0,"zan":0}]
     * offset : 0
     * over : true
     * pageCount : 1
     * size : 20
     * total : 5
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<DatasBean> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * author : xiaoyang
         * chapterId : 440
         * chapterName : 官方
         * courseId : 13
         * desc : <p>在我们的印象里，如果构造一个 Dialog 传入一个非 Activiy 的 context，则可能会出现 bad token exception。</p>
         * <p>今天我们就来彻底搞清楚这一块，问题来了：</p>
         * <ol>
         * <li>为什么传入一个非 Activity 的 context 会出现错误？</li>
         * <li>传入的 context 一定要是 Activity 吗？</li>
         * </ol>
         * envelopePic :
         * id : 199721
         * link : https://www.wanandroid.com/wenda/show/18281
         * niceDate : 2021-06-15 09:06
         * origin :
         * originId : 18281
         * publishTime : 1623719193000
         * title : 每日一问 | Dialog 的构造方法的 context 必须传入 Activity吗？
         * userId : 97982
         * visible : 0
         * zan : 0
         */

        private String author;
        private int chapterId;
        private String chapterName;
        private int courseId;
        private String desc;
        private String envelopePic;
        private int id;
        private String link;
        private String niceDate;
        private String origin;
        private int originId;
        private long publishTime;
        private String title;
        private int userId;
        private int visible;
        private int zan;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getChapterId() {
            return chapterId;
        }

        public void setChapterId(int chapterId) {
            this.chapterId = chapterId;
        }

        public String getChapterName() {
            return chapterName;
        }

        public void setChapterName(String chapterName) {
            this.chapterName = chapterName;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnvelopePic() {
            return envelopePic;
        }

        public void setEnvelopePic(String envelopePic) {
            this.envelopePic = envelopePic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getNiceDate() {
            return niceDate;
        }

        public void setNiceDate(String niceDate) {
            this.niceDate = niceDate;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public int getOriginId() {
            return originId;
        }

        public void setOriginId(int originId) {
            this.originId = originId;
        }

        public long getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(long publishTime) {
            this.publishTime = publishTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVisible() {
            return visible;
        }

        public void setVisible(int visible) {
            this.visible = visible;
        }

        public int getZan() {
            return zan;
        }

        public void setZan(int zan) {
            this.zan = zan;
        }
    }
}
