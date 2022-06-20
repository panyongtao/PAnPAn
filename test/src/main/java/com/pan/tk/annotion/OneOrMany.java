package com.pan.tk.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 如果没有继承BeanManual 需要指定mapper, method, parameter*关联表映射的字段
 * target当前类目标字段*to目标类条件字段
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OneOrMany {
    //如果不是继承BeanManual,其他一般形式的mapper使用如下
    Class mapper () default void.class;
    String method() default "";
    Class parameter() default void.class;
    String target() default "";
    String to() default "" ;

}
