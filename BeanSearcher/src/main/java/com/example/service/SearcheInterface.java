package com.example.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.spring.SpringUtil;
import com.ejlchina.searcher.BeanSearcher;
import com.ejlchina.searcher.MapSearcher;
import com.ejlchina.searcher.SearchResult;
import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用BeanSearcher接口要求类中要有params参数
 */
public interface SearcheInterface {
    /**
     * 获取参数
     * @return
     */
    Map<String, Object> getParams() ;

    /**
     * 获取统计的字段list
     * @return
     */
    List<String> getSummerFieldList();

    /**
     * 获取单个统计的字段
     * @return
     */
    String getSummeryField();
    /**
     * 获取BeanSearcher
     * @return
     */
    default BeanSearcher beanSearcher(){
        return SpringUtil.getBean(BeanSearcher.class);
    }
    /**
     * 获取MapSearcher
     * @return
     */
    default MapSearcher mapSearcher(){
        return SpringUtil.getBean(MapSearcher.class);
    }

    /**
     * 计算查询的数量
     * @return
     */
    default Number searchCount(){
        return beanSearcher().searchCount(this.getClass() , getParams());
    }
    /**
     * 获取封装的数据，适合需要分页的查询
     * @return
     */
    default SearchResult search(){
        SearchResult o;
        List<String> summerFieldList=getSummerFieldList();
        if(CollectionUtils.isNotEmpty(summerFieldList)){
            String[] summerFieldArray=new String[summerFieldList.size()];
            for (int i = 0; i < summerFieldList.size(); i++) {
                summerFieldArray[i]=summerFieldList.get(i);
            }
            o=beanSearcher().search(this.getClass() , getParams(), summerFieldArray);
        }else{
            o=beanSearcher().search(this.getClass() , getParams());
        }
        return o;
    }
    /**
     * 根据条件只查询其中一条
     * @return
     */
     default Object searchFirst(){
        return beanSearcher().searchFirst(this.getClass() , getParams());
    }
    /**
     * 查询list列表
     * @return
     */
    default List searchList(){
        return beanSearcher().searchList(this.getClass() , getParams());
    }
    /**
     * 查询list map
     * @return
     */
    default List<Map<String, Object>> searchMap(){
        return mapSearcher().searchList(this.getClass() , getParams());
    }
    /**
     * 查询所有
     * @return
     */
    default List  searchAll(){
        return beanSearcher().searchAll(this.getClass() , getParams());
    }
    /**
     * 字段求和统计
     * @return
     */
    default Number searchSum(){
        return beanSearcher().searchSum(this.getClass() , getParams(),getSummeryField());
    }

    /**
     * 多个字段求和统计
     * @return
     */
    default Number[] searchManySum(){
        List<String> summerFieldList=getSummerFieldList();
        if(CollectionUtils.isNotEmpty(summerFieldList)) {
            String[] summerFieldArray = new String[summerFieldList.size()];
            for (int i = 0; i < summerFieldList.size(); i++) {
                summerFieldArray[i] = summerFieldList.get(i);
            }
            return beanSearcher().searchSum(this.getClass() , getParams(),summerFieldArray);
        }
        return null;
    }

    /**
     * 转为map
     * @return
     */
    default Map<String,Object> toMap(){
        if(this==null){
            return new HashMap<>();
        }else{
            return Convert.toMap(String.class,Object.class,this);
        }
    }
}
