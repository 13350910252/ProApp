package com.yinp.proapp.module.wanandroid.bean;

import java.util.List;

public class RankListBean {
    /**
     * curPage : 2
     * datas : [{"coinCount":17085,"level":171,"nickname":"","rank":"31","userId":1580,"username":"k**od21"},{"coinCount":16786,"level":168,"nickname":"","rank":"32","userId":12467,"username":"c**yie"},{"coinCount":16777,"level":168,"nickname":"","rank":"33","userId":2657,"username":"a**111993@163.com"},{"coinCount":16070,"level":161,"nickname":"","rank":"34","userId":30114,"username":"E**an_Jin"},{"coinCount":15596,"level":156,"nickname":"","rank":"35","userId":20592,"username":"c**hao9808"},{"coinCount":15531,"level":156,"nickname":"","rank":"36","userId":16289,"username":"Ａ**ｒｏｉｄ"},{"coinCount":15470,"level":155,"nickname":"","rank":"37","userId":42260,"username":"C**nY"},{"coinCount":15381,"level":154,"nickname":"","rank":"38","userId":21156,"username":"s**skrskr"},{"coinCount":15357,"level":154,"nickname":"","rank":"39","userId":9621,"username":"S**24n"},{"coinCount":15315,"level":154,"nickname":"","rank":"40","userId":7541,"username":"l**64301766"},{"coinCount":15183,"level":152,"nickname":"","rank":"41","userId":23244,"username":"a**ian"},{"coinCount":14922,"level":150,"nickname":"","rank":"42","userId":13273,"username":"1**17315362"},{"coinCount":14815,"level":149,"nickname":"","rank":"43","userId":20375,"username":"z**hailong"},{"coinCount":14778,"level":148,"nickname":"","rank":"44","userId":29076,"username":"f**ham"},{"coinCount":14436,"level":145,"nickname":"","rank":"45","userId":11828,"username":"1**12756139"},{"coinCount":14381,"level":144,"nickname":"","rank":"46","userId":17645,"username":"F**tasyLin"},{"coinCount":14281,"level":143,"nickname":"","rank":"47","userId":29811,"username":"虚**菜鸡"},{"coinCount":14070,"level":141,"nickname":"","rank":"48","userId":28607,"username":"S**Brother"},{"coinCount":13920,"level":140,"nickname":"","rank":"49","userId":14951,"username":"1**86223547"},{"coinCount":13872,"level":139,"nickname":"","rank":"50","userId":12785,"username":"c**nxiao"},{"coinCount":13768,"level":138,"nickname":"","rank":"51","userId":9778,"username":"1**11985351"},{"coinCount":13734,"level":138,"nickname":"","rank":"52","userId":14854,"username":"z**23456"},{"coinCount":13702,"level":138,"nickname":"","rank":"53","userId":7075,"username":"c**ndroid"},{"coinCount":13690,"level":137,"nickname":"","rank":"54","userId":21500,"username":"t**njianlong"},{"coinCount":13605,"level":137,"nickname":"","rank":"55","userId":21141,"username":"1**88023205"},{"coinCount":13585,"level":136,"nickname":"","rank":"56","userId":15975,"username":"Y**gQR"},{"coinCount":13524,"level":136,"nickname":"","rank":"57","userId":27602,"username":"l**hehan"},{"coinCount":13516,"level":136,"nickname":"","rank":"58","userId":27535,"username":"1**08491840"},{"coinCount":13506,"level":136,"nickname":"","rank":"59","userId":4869,"username":"o**n_9527"},{"coinCount":13209,"level":133,"nickname":"","rank":"60","userId":35910,"username":"h**mee"}]
     * offset : 30
     * over : false
     * pageCount : 2482
     * size : 30
     * total : 74435
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
         * coinCount : 17085
         * level : 171
         * nickname :
         * rank : 31
         * userId : 1580
         * username : k**od21
         */

        private int coinCount;
        private int level;
        private String nickname;
        private String rank;
        private int userId;
        private String username;

        public int getCoinCount() {
            return coinCount;
        }

        public void setCoinCount(int coinCount) {
            this.coinCount = coinCount;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
