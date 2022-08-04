package pan.post;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @Author pan
 * @Date 2022/8/3 10:06
 * @Version 1.0
 */
@Component
public class MyBeanPostProccer implements BeanFactoryPostProcessor {
    /*Bean定义之后执行BeanFactoryPostProcessor*/
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("执行了我自己的后置处理器");
        Iterator<String> beanNamesIterator = configurableListableBeanFactory.getBeanNamesIterator();
        while (beanNamesIterator.hasNext()){
            System.out.println(beanNamesIterator.next());
        }

    }
}
