package pan;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import pan.pojo.InitializerBean;
import pan.pojo.Item;
import pan.pojo.MyBeanPost;

import java.util.Locale;
import java.util.Set;

@SpringBootApplication
public class Application {

    @Bean
    @ConditionalOnProperty("server.port")
    public Item it(){
        return new Item();
    }

    @Bean
    @ConditionalOnResource(resources="http://www.baidu.com")
    public Item it1(){
        return new Item();
    }
    @Bean
    @ConditionalOnJava(JavaVersion.EIGHT)
    public Item it2(){
        return new Item();
    }
    @Bean
    @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
    public Item it3(){
        return new Item();
    }

    @Bean
    @ConditionalOnExpression("${a}==true")
    public Item it4(){
        return new Item();
    }
//    @Bean  实际上是根据属性判断平台
//    @ConditionalOnCloudPlatform(CloudPlatform.NONE)
//    public Item it5(){
//        return new Item();
//    }

    @Bean
    @Profile("dev")
    public Item it6(){
        return new Item();
    }
    @Bean
    @Profile("sit")
    public Item it7(){
        return new Item();
    }

    // 利用该对象实现资源文件的读取
     @Autowired
     private MessageSource messageSource;
    /*模拟方法注解*/
    @SneakyThrows
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory();
        MetadataReader metadataReader = factory.getMetadataReader(new ClassPathResource("pan/config/MyGeConfig.class"));
        Set<MethodMetadata> methods = metadataReader.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName());
        for (MethodMetadata method : methods) {
            System.out.println(method.getMethodName());
        }
    }

    /**
     * 模拟包扫描
     * @param args
     */
    @SneakyThrows
    public static void main3(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        System.out.println(context.getBean(MyBeanPost.class));
    }

    @SneakyThrows
    public static void main2(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        String welcome = run.getMessage("name", null, Locale.CHINESE);
        System.out.println(welcome);

        Resource resource = run.getResource("classpath:META-INF/spring.factories");
        System.out.println(resource.getFilename());

        Resource[] resources = run.getResources("classpath*:META-INF/spring.factories");
        for (Resource resource1 : resources) {
            System.out.println(resource1.getFilename());
        }

        String[] beanDefinitionNames = run.getBeanDefinitionNames();

        for (String name : beanDefinitionNames) {
//            System.out.println(name+":"+run.getBean(name).getClass().getTypeName());
//            System.out.println(run.getBean(name).getClass().getTypeName());
        }
        System.out.println(beanDefinitionNames);
        String property = run.getEnvironment().getProperty("server.port");
        System.out.println(property);

    }

    public static void main1(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
//        System.out.println(run.getBean(Item.class));
        //使用spring.factories也是可以的
        System.out.println(run.getBean(MyConfig.class));
        System.out.println(run.getBean(InitializerBean.class));
        System.out.println(run.getBean("it"));
        System.out.println(run.getBean("it1"));
        System.out.println(run.getBean("it2"));
        System.out.println(run.getBean("it3"));
        System.out.println(run.getBean("it4"));
        System.out.println(run.getBean("it6"));
//        System.out.println(run.getBean("it7"));
    }
}
