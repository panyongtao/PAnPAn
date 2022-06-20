package com.pan.mapper;


import com.pan.pojo.TkHero;
import com.pan.tk.TkMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HeroTkMapper extends TkMapper<TkHero> {  //2.继承通用Mapper类

}
