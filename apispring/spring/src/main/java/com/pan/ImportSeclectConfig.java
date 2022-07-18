package com.pan;

import com.pan.service.ImportSelectBean;
import com.pan.service.ImportSelector;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * @Author pan
 * @Date 2022/7/18 14:22
 * @Version 1.0
 * FilterType 可以是注解也可以是接口，切面
 */
@Import(ImportSelector.class)//导入的bean其实是一个配置bean
public class ImportSeclectConfig {

    public static void main(String[] args) {
        //哪怕AppConfig不加任何注解都行 但只限于本类
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ImportSeclectConfig.class);
        ImportSelectBean bean = context.getBean(ImportSelectBean.class);
        System.out.println(bean);
    }
}
