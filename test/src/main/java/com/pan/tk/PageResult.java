package com.pan.tk;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> {
    //数据结果集
    protected List<T> list;
    //分页计算结果
    protected Pagenation pagenation;

    @Data
    public static class Pagenation implements Serializable {
        private static final long serialVersionUID = -5294394036177528927L;
        //当前页
        private int pageNum;//每页的数量
        private int pageSize;//当前页的数量
        private int size;
        //由于startRow利lendRow不常用，这里说个具体的用法
        // 可以在页面中"显示startRow到endRow 共size条数据"
        //当前页面第一个元素在数据库中的行号
        private int startRow;
        //当前页面最后一个元素在数据库中的行号
        private int endRow;
        //总记录数
        private long total;
        //总页数
        private int pages;
        //前一页
        private int prePage;
        //下一页
        private int nextPage;
        //是否为第一页
        private boolean isFirstPage = false;//是否为最后一页
        private boolean isLastPage = false;//是否有前一页
        private boolean hasPreviousPage = false;//是否有下一页
        private boolean hasNextPage = false;//导航页码数
        private int navigatePages;//所有导航页号
        private int[] navigatepageNums;
        //导航条上的第一页
        private int navigateFirstPage;
        //导航条上的最后一页
        private int navigateLastPage;

        /**
         * 计算导航页
         */
        public void calcNavigatepageNums() {
            //当总页数小于或等于导航页码数时
            if (pages <= navigatePages) {
                navigatepageNums = new int[pages];
                for (int i = 0; i < pages; i++) {
                    navigatepageNums[i] = i + 1;
                }
            } else { //当总页数大于导航页码数时
                navigatepageNums = new int[navigatePages];
                int startNum = pageNum - navigatePages / 2;
                int endNum = pageNum + navigatePages / 2;
                if (startNum < 1) {
                    startNum = 1;
                    //(最前navigatePages页
                    for (int i = 0; i < navigatePages; i++) {
                        navigatepageNums[i] = endNum--;
                    }
                } else {
                    //所有中间页
                    for (int i = 0; i < navigatePages; i++) {
                        navigatepageNums[i] = startNum++;
                    }
                }
            }
        }
        /**
         * 计算前后页，第一页最后一页
         */
        public void calcPage(){
            if (navigatepageNums != null && navigatepageNums.length > 0){
                navigateFirstPage = navigatepageNums[0] ;
                navigateLastPage = navigatepageNums[navigatepageNums.length - 1];if (pageNum > 1){
                    prePage = pageNum - 1;
                }
                if (pageNum < pages){
                    nextPage = pageNum + 1;
                }
            }
        }

        /**
         * 判定边界页
         */
        public void judgePageBoudary() {
            isFirstPage = pageNum == 1;
            isLastPage = pageNum ==pages || pages == 0;
            hasPreviousPage = pageNum > 1;
            hasNextPage = pageNum < pages;
        }

    }




}
