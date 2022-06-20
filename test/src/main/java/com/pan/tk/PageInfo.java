package com.pan.tk;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

public class PageInfo<T> extends PageResult implements Serializable {
    public PageInfo(){
    }
    public PageInfo(List<T> list){ this(list, 8); }

    public PageInfo(List<T> list,int navigatePages) {
        Pagenation pagenation = new Pagenation();
        if (list instanceof Page) {
            Page page = (Page) list;
            pagenation.setPageNum(page.getPageNum());
            pagenation.setPageSize(page.getPageSize());
            pagenation.setPages(page.getPages());
            pagenation.setSize(page.size());
            pagenation.setTotal(page.getTotal());
            //由于结果是>startRow的，所以实际的需要+1
            if (pagenation.getSize() == 0) {
                pagenation.setStartRow(0);
                pagenation.setEndRow(0);
            } else {
                pagenation.setStartRow((int) (page.getStartRow() + 1));
                pagenation.setEndRow(pagenation.getStartRow() - 1 + pagenation.getSize());
            }
        } else {
            pagenation.setPageNum(1);
            pagenation.setPageSize(list.size());
            pagenation.setPages(pagenation.getPageSize() > 0 ? 1 : 0);
            pagenation.setSize(list.size());
            pagenation.setTotal(list.size());
            pagenation.setStartRow(0);
            pagenation.setEndRow(list.size() > 0 ? list.size() - 1 : 0);
        }
        pagenation.setNavigatePages(navigatePages);//计算导航页
        pagenation.calcNavigatepageNums();
        //计算前后页，第一页，最后一页
        pagenation.calcPage();
        //判断页面边界
        pagenation.judgePageBoudary();
        this.setList(list);
        this.setPagenation(pagenation);
    }
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageInfo{");
        sb.append("， list=").append(list);
        if (pagenation != null) {
            sb.append(" pageNum=").append(pagenation.getPageNum());
            sb.append("， pageSize=").append(pagenation.getPageSize());
            sb.append("， size=").append(pagenation.getSize());
            sb.append("， startRow=").append(pagenation.getStartRow());
            sb.append(",  endRow=").append(pagenation.getEndRow());
            sb.append("， total=").append(pagenation.getTotal());
            sb.append("， pages=").append(pagenation.getPages());
            sb.append("， prePage=").append(pagenation.getPrePage());
            sb.append(",  nextPage=").append(pagenation.getNextPage());
            sb.append("， isFirstPage=").append(pagenation.isFirstPage());
            sb.append("， isLastPage=").append(pagenation.isLastPage());
            sb.append(",  hasPreviousPage=").append(pagenation.isHasPreviousPage());
            sb.append("， hasNextPage=").append(pagenation.isHasNextPage());
            sb.append(",  navigatePages=").append(pagenation.getNavigatePages());
            sb.append(", navigateFirstPage=").append(pagenation.getNavigateFirstPage());
            sb.append(", navigateLastPage=").append(pagenation.getNavigateLastPage());
            sb.append("， navigatepageNums=");
            if (pagenation.getNavigatepageNums() == null) {
                sb.append("null");
            } else {
                sb.append('[');
                for (int i = 0; i < pagenation.getNavigatepageNums().length; ++i)
                    sb.append(i == 0 ? "" : "，").append(pagenation.getNavigatepageNums()[i]);
                sb.append(']');
            }
        }
        sb.append('}');
        return sb.toString();
    }
 }
