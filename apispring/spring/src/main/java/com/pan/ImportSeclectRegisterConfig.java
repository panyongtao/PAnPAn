package com.pan;

import com.pan.service.ImportSelectorRegister;
import com.pan.service.RegisterBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * @Author pan
 * @Date 2022/7/18 14:22
 * @Version 1.0
 * FilterType 可以是注解也可以是接口，切面
 */
@Import(ImportSelectorRegister.class)//导入的bean其实是一个配置bean
public class ImportSeclectRegisterConfig {

    public static void main(String[] args) {
        //哪怕AppConfig不加任何注解都行 但只限于本类
        AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(ImportSeclectRegisterConfig.class);
        RegisterBean bean = context.getBean(RegisterBean.class);
        System.out.println(bean);
    }
}
