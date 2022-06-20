package com.pan.mapper;


import com.pan.pojo.Hero;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface HeroMapper extends Mapper<Hero> {  //2.继承通用Mapper类
}
