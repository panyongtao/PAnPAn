package com.example.sbean;

import com.ejlchina.searcher.bean.SearchBean;
import lombok.Data;

/**
 * 省略 @SearchBean #
 * 当 Bean Searcher 找不到 @SearchBean 注解（v3.2 开始会自动寻找父类的 @SearchBean 注解），或 @SearchBean 注解内没有指定 tables 属性时，会认为该实体类是一个 单表映射 实体类。此时的表名将服从自动映射规则：
 *
 * 表名 = 前缀 + 根据配置是否转为大写（驼峰转小写下划线（去掉冗余后缀的类名））
 * 实体类省略了 @SearchBean 注解
 * 实体类的 @SearchBean 没有指定 tables 属性
 * 实体类的 @SearchBean 指定了 autoMapTo 属性
 */
@SearchBean(tables="tb_hero t",autoMapTo ="t" )
@Data
public class TbHeroExtend extends TbHero {
    private String phone;
}
