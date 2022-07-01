package com.pan.pojo;

import com.ejlchina.searcher.bean.SearchBean;
import com.pan.mapper.HeroTkMapper;
import com.pan.tk.BeanManual;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "tb_hero")  //3.给通用mapper找表用
@SearchBean(tables="tb_hero t",autoMapTo = "t")
public class TkHero extends BeanManual<HeroTkMapper,TkHero> implements Serializable {
    @Id  //主键
    @KeySql(useGeneratedKeys = true)
    private Integer id;
    private String username;
    private String profession;
    @Transient  //不扫描此字段
    private String phone;
    private String email;
    private Date onlinetime;
}
