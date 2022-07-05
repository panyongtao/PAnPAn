package com.pan.datasource.config;

import com.pan.datasource.DynamicDataSource;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * 添加动态数据源的mybatis其他配置，以及添加事务
 */
@Configuration
public class DatasourceConfig {
    @Autowired
    DynamicDataSource dynamicDataSource;
    @Value("com.pan.mapper")
    private String typeAliasesPackage ;
    @Value("classpath*:mapper/*.xml")
    private String mapperLocations;
    @Value("${mybatis.configuration.log-impl:}")
    private String logClassStr;

    /**
     * 设置会话工厂，用来生成session会话
     * @return
     */
    @Bean
    @SneakyThrows
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver() ;
        bean.setMapperLocations(resolver.getResources(mapperLocations));
        bean.setTypeAliasesPackage(typeAliasesPackage);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true) ;
        if (StringUtils.isNotBlank(logClassStr)) {
            configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
        }
//        bean.setPlugins(new PageInterceptor());
        return bean;
    }

    /**
     * 设置包扫描此处可以用来动态扫描包
     * @return
     */
    //@Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer config = new MapperScannerConfigurer();
        config.setBasePackage("com/pan/dao");
        return config;
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dynamicDataSource);
        return manager;
    }
}
