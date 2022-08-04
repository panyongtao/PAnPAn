package pan.post;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;
import pan.ano.PostAno;

/**
 * @Author pan
 * @Date 2022/8/3 10:06
 * @Version 1.0
 */
@Component
public class RegBeanPostProccer implements BeanFactoryPostProcessor {
    /*Bean定义之后执行BeanFactoryPostProcessor*/
    @Override
    @SneakyThrows
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("执行了我自己的后置处理器");
        String p="pan.pojo";
        String path = "classpath*:" + p.replace(  ".","/")+"/**/*.class" ;
        System.out.println(path);
        CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
        AnnotationBeanNameGenerator generator=new AnnotationBeanNameGenerator();
        for (Resource resource : resources) {
            System.out.println(resource);
            MetadataReader reader = factory.getMetadataReader(resource);
            System.out.println("类名;" + reader.getClassMetadata().getClassName());
            AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
            System.out.println("是否加了@PostAno:" + reader. getAnnotationMetadata(). hasAnnotation(PostAno.class. getName()));
            System.out. println("是否加了@PostAno 派生:"+ reader.getAnnotationMetadata().hasMetaAnnotation(PostAno.class. getName()));
            //模拟BeanFactoryPostProcessor注入类
            if (annotationMetadata. hasAnnotation(PostAno.class.getName())
                    || annotationMetadata.hasMetaAnnotation(PostAno.class.getName())) {
                AbstractBeanDefinition bd = BeanDefinitionBuilder
                        .genericBeanDefinition(reader.getClassMetadata().getClassName()).getBeanDefinition();
                if(configurableListableBeanFactory instanceof DefaultListableBeanFactory ){
                    DefaultListableBeanFactory beanFactory= (DefaultListableBeanFactory) configurableListableBeanFactory;
                    String name = generator.generateBeanName(bd,beanFactory);
                    beanFactory.registerBeanDefinition(name,bd);
                }

            }
        }
    }
}
