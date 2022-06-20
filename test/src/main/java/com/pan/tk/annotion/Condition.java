package com.pan.tk.annotion;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 所有条件的查询要建立在不为空,list要有元素
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {
    String value () default "";
    //预留值暂未使用
    String secondValue() default"";
    String end() default "";
    //预留值暂未使用)
    String sql() default"";
    String likeType() default "";
    //指向的列(实体类的属性)
    String column() default"";
}
