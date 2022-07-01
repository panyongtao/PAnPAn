package com.example.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.extra.spring.SpringUtil;
import com.ejlchina.searcher.BeanSearcher;
import com.ejlchina.searcher.MapSearcher;
import com.ejlchina.searcher.SearchResult;
import com.ejlchina.searcher.bean.DbIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.formula.functions.T;

import javax.persistence.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BeanSearcher公共的服务抽象类,前端请求示例如下，也可以扩充计算规则
 * 1.GET /user/index
 * 2.GET /user/index? page=2 & size=10    分页查询
 * 3.GET /user/index? sort=age & order=desc   排序
 * 4.GET /user/index? onlySelect=id,name      指定查询字段
 * 5.GET /user/index? selectExclude=age       排除查询字段
 * 6.GET /user/index? age=20
 * 7.GET /user/index? age=20 & age-op=eq    6.7查询结果相同
 * 8.GET /user/index? age=20 & age-op=ne    ne 是 NotEqual 的缩写
 * GET /user/index? age=20 & age-op=ge      ge 是 GreateEqual 的缩写
 * GET /user/index? age=20 & age-op=le      le 是 LessEqual 的缩写
 * GET /user/index? age=20 & age-op=gt      gt 是 GreateThan 的缩写
 * GET /user/index? age=20 & age-op=lt      lt 是 LessThan 的缩写
 * GET /user/index? age-0=20 & age-1=30 & age-op=bt     返回 20 <= age <= 30 的数据，bt 是 Between 的缩写
 * 优化：觉得 age-0=20 & age-1=30 有点复杂，想用 age=[20,30] 替代？可以的！请参考 参数过滤器 章节。
 * GET /user/index? age-0=20 & age-1=30 & age-2=40 & age-op=il  返回 age in (20, 30, 40) 的数据，il 是 InList 的缩写
 * 优化   同理 age-0=20 & age-1=30 & age-2=40      可优化为 age=[20,30,40]，参考 参数过滤器 章节。
 * GET /user/index? name=Jack & name-op=ct       返回 name 包含 Jack 的数据，ct 是 Contain 的缩写
 * GET /user/index? name=Jack & name-op=sw      返回 name 以 Jack 开头的数据，sw 是 StartWith 的缩写
 * GET /user/index? name=Jack & name-op=ew      ew 是 EndWith 的缩写
 * GET /user/index? name-op=ey      ey 是 Empty 的缩写
 * GET /user/index? name-op=ny      ny 是 NotEmpty 的缩写
 * GET /user/index? name=Jack & name-ic=true    ic 是 IgnoreCase 的缩写
 * 补充
 * OrLike	ol	x like ?1 or x like ?2 or ...	是	模糊或匹配（可有多个参数值）（since v3.7）
 * InList	il / mv	x in (?, ?, ...)	是	多值查询（InList / il 自 v3.3 新增，之前是 MultiValue / mv）
 * NotIn	ni	x not in (?, ?, ...)	是	多值查询（since v3.3）
 * IsNull	nl	x is null	否	为空（since v3.3）
 * NotNull	nn	x is not null	否	不为空（since v3.3）
 */

/**
 * 后端参数装配示例
 *
 */
@Data
public abstract class CommonSearcherBean {
    @Transient
    @JsonIgnore
    @DbIgnore
    //核心传参，方便bean类通过内部属性params来传参
    protected Map<String, Object> params=new HashMap<>();
    @Transient
    @JsonIgnore
    @DbIgnore
    //当前bean类
    private Class beanSearcherClass;
    @Transient
    @JsonIgnore
    @DbIgnore
    //统计字段list
    protected List<String> summerFieldList;
    @Transient
    @JsonIgnore
    @DbIgnore
    //单个统计字段
    protected String summeryField;
    public CommonSearcherBean() {
        //获取泛型类型
        beanSearcherClass = this.getClass();
    }

    /**
     * 获取封装的数据，适合需要分页的查询
     * @return
     */
    public SearchResult<T> search(){
        SearchResult<T> o;
        if(CollectionUtils.isNotEmpty(summerFieldList)){
            String[] summerFieldArray=new String[summerFieldList.size()];
            for (int i = 0; i < summerFieldList.size(); i++) {
                summerFieldArray[i]=summerFieldList.get(i);
            }
            o=beanSearcher().search(beanSearcherClass , params, summerFieldArray);
        }else{
            o=beanSearcher().search(beanSearcherClass , params);
        }
       return o;
    }

    /**
     * 根据条件只查询其中一条
     * @return
     */
    public Object searchFirst(){
       return beanSearcher().searchFirst(beanSearcherClass , params);
    }

    /**
     * 查询list列表
     * @return
     */
    public List<T> searchList(){
        return beanSearcher().searchList(beanSearcherClass , params);
    }

    /**
     * 查询list map
     * @return
     */
    public List<Map<String, Object>> searchMap(){
        return mapSearcher().searchList(beanSearcherClass , params);
    }

    /**
     * 查询所有
     * @return
     */
    public List<T>  searchAll(){
        return beanSearcher().searchAll(beanSearcherClass , params);
    }

    /**
     * 计算查询的数量
     * @return
     */
    public Number searchCount(){
        return beanSearcher().searchCount(beanSearcherClass , params);
    }

    /**
     * 字段求和统计
     * @return
     */
    public Number searchSum(){
        return beanSearcher().searchSum(beanSearcherClass , params,summeryField);
    }

    /**
     * 多个字段求和统计
     * @return
     */
    public Number[] searchManySum(){
        if(CollectionUtils.isNotEmpty(summerFieldList)) {
            String[] summerFieldArray = new String[summerFieldList.size()];
            for (int i = 0; i < summerFieldList.size(); i++) {
                summerFieldArray[i] = summerFieldList.get(i);
            }
            return beanSearcher().searchSum(beanSearcherClass , params,summerFieldArray);
        }
        return null;
    }

    /**
     * 转为map
     * @return
     */
    public Map<String,Object> toMap(){
        if(this==null){
            return new HashMap<>();
        }else{
            return Convert.toMap(String.class,Object.class,this);
        }
    }

    private BeanSearcher beanSearcher(){
        return SpringUtil.getBean(BeanSearcher.class);
    }
    private MapSearcher mapSearcher(){
        return SpringUtil.getBean(MapSearcher.class);
    }
}
