package pan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.system.JavaVersion;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import pan.pojo.InitializerBean;
import pan.pojo.Item;

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

    public static void main(String[] args) {
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
