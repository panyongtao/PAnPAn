package pan.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//会自动找application.properties文件
@Configuration
@EnableConfigurationProperties({EnableMyProperties.class})
public class JdbcConfig {
    /**
     * 将属性绑定到返回的Bean对象上,属于后绑定
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource  dataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
    @Bean
    public EnableMyProperties test(EnableMyProperties enableMyProperties){
        System.out.println(enableMyProperties);
        return new EnableMyProperties();
    }

}
