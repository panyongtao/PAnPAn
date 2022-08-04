package pan.test;

import cn.hutool.extra.spring.SpringUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pan.Application;

import java.util.Locale;

/**
 * @Author pan
 * @Date 2022/8/3 9:02
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
public class AppTest {
    // 利用该对象实现资源文件的读取
    @Autowired
    private MessageSource messageSource;
    @Test
    public void test(){
        // 利用该对象实现资源文件的读取,必须也要国际化才行
        System.out.println(messageSource.getMessage("name", null, Locale.SIMPLIFIED_CHINESE));
        System.out.println(messageSource.getClass().getTypeName());
        System.out.println(SpringUtil.getBean(ThreadPoolTaskExecutor.class));
        System.out.println(SpringUtil.getBean(DataSourceProperties.class));
    }

    /**
     * BeanFactory后置处理器
     */
    @Test
    public void test1(){
        SpringUtil.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(v->{
            System.out.println(v);
        });
    }
    /**
     * Bean后置处理器
     */
    @Test
    public void test2(){
        SpringUtil.getBeansOfType(BeanPostProcessor.class).values().forEach(v->{
            System.out.println(v);
        });
    }
}
