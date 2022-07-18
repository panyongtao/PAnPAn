package com.pan.service.condition;

import com.pan.ImportConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * @Author pan
 * @Date 2022/7/18 17:05
 * @Version 1.0
 * 测试条件注解
 */
@Component
@Conditional(MyCondition.class)
public class ConditionBean {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ImportConfig.class);
        ConditionBean bean = context.getBean(ConditionBean.class);
        System.out.println(bean);
    }
}
