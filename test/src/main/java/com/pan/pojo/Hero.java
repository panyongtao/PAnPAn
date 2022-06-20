package com.pan.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Table(name = "tb_hero")  //3.给通用mapper找表用
public class Hero {
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
