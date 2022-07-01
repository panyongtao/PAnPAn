package com.pan.pojo;

import com.ejlchina.searcher.bean.DbField;
import com.ejlchina.searcher.bean.DbIgnore;
import com.ejlchina.searcher.bean.SearchBean;
import com.ejlchina.searcher.operator.Equal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pan.BeanSearcher.SearcheInterface;
import lombok.Data;

import javax.persistence.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@SearchBean(tables="tb_hero t,cable c",joinCond = "t.id=c.hero_id",autoMapTo = "t")
public class TbHero extends TkHero implements SearcheInterface {
    @Transient
    @JsonIgnore
    @DbIgnore
    //核心传参，方便bean类通过内部属性params来传参
    protected Map<String, Object> params=new HashMap<>();
    //统计字段list
    @Transient
    @JsonIgnore
    @DbIgnore
    protected List<String> summerFieldList;
    @Transient
    @JsonIgnore
    @DbIgnore
    //单个统计字段
    protected String summeryField;
    private String username;
    @DbField(value = "c.cable_name",onlyOn = {Equal.class})
    private String cableName;
}
