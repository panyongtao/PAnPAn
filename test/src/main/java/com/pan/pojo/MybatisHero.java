package com.pan.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.ejlchina.searcher.bean.SearchBean;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

@Data
@TableName( "tb_hero")  //3.给通用mapper找表用
@SearchBean(tables="tb_hero")
public class MybatisHero extends Model<MybatisHero>  {
    private Integer id;
    private String username;
    private String profession;
    @Transient  //不扫描此字段
    private String phone;
    private String email;
    private Date onlinetime;
}
