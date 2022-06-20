package com.pan.tk.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关扩充低版本通用mapper逻辑删除（标记注解)，只匹配第一个查到的
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TkLogicDelete {
    int isDeletedValue() default 1;
    int notDeletedValue() default 0;

}
