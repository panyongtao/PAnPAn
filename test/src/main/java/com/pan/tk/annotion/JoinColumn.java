package com.pan.tk.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关联表映射
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinColumn {
    //下面的语句出现必须是成对的
    // 表名可以是sql语句拼成的表
    String[] table() default"";
    //表的别名
    String[] alias() default"";
    //对应的目的表的列
    String[] column() default "";
    //连接类型
    String[] joinType() default "";

}
