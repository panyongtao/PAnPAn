package com.pan.mapper;


import com.pan.pojo.OracleTkHero;
import com.pan.tk.TkMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OracleHeroTkMapper extends TkMapper<OracleTkHero> {  //2.继承通用Mapper类

}
