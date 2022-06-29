//package com.pan.datasource.doublemybatisdatasource;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.google.common.base.Throwables;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//
//@Configuration
//@EnableTransactionManagement
//@MapperScan(basePackages = {"com.pan.dao","com.pan.mapper"},sqlSessionFactoryRef = "mysqlSessionFactory")
//@Slf4j
//public class MysqlDataSource {
//    @Value("${spring.datasource.url}")
//    private String url;
//    @Value("${spring.datasource.username}")
//    private String username ;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName ;
//    @Value("com.pan.mapper")
//    private String typeAliasesPackage ;
//    @Value("classpath*:mapper/*.xml")
//    private String mapperLocations;
//
//    @Value("200")
//    private int maxActive;
//    @Value("1")
//    private int minIdle;
//    @Value("30")
//    private int queryTimeout;
//    @Value("30")
//    private int loginTimeout;
//    @Value("50")
//    private int initialsize;
//    @Value("config")
//    private String filters;
//    @Value ("60000")
//    private int maxWait;
//    @Value("${mybatis.configuration.log-impl}")
//    private String logClassStr;
//
//    @Bean (name = "mysqldatasource", initMethod = "init", destroyMethod = "close")
//    public DruidDataSource dataSource() {
//        return createDataSource (url,username,password, driverClassName);
//    }
//
//    @Primary
//    @Bean("mysqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("mysqldatasource") DataSource datasourceJira)
//            throws Exception {
//        return sqlSessionFactory(datasourceJira, mapperLocations, typeAliasesPackage,logClassStr);
//    }
//
//    @Primary
//    @Bean ("mysqltransactionManager")
//    public PlatformTransactionManager transactionManager(@Qualifier("mysqldatasource") DataSource datasourceJira) {
//        return new DataSourceTransactionManager(datasourceJira);
//    }
//    private SqlSessionFactory sqlSessionFactory(DataSource dataSource,String mapperLocations,String typeAliasesPackage,String logClassStr)
//            throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean() ;
//        sqlSessionFactoryBean.setDataSource(dataSource) ;
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver() ;
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
//        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
//        configuration.setMapUnderscoreToCamelCase(true) ;
//        if (StringUtils.isNotBlank(logClassStr)) {
//            configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
//        }
//        sqlSessionFactoryBean. setConfiguration(configuration) ;
//        return sqlSessionFactoryBean.getObject() ;
//    }
//    @SneakyThrows
//    private DruidDataSource createDataSource(String url,String username,String password,String driverClassName) {
//        if (StringUtils.isEmpty(url) || StringUtils.isBlank(username) || StringUtils.isBlank(driverClassName)) {
//            log.error("数据库配置异常,请检查参数配置");
//            throw new Exception("数据库配置信息异常");
//        }
//        DruidDataSource datasource = new DruidDataSource();
//        datasource.setUrl(url);
//        datasource.setUsername(username);
//        datasource.setPassword(password);
//        datasource.setDriverClassName(driverClassName);
//        datasource.setInitialSize(initialsize);
//        datasource.setMinIdle(minIdle);
//        datasource.setMaxActive(maxActive);
//        datasource.setMaxWait(maxWait);
//        datasource.setQueryTimeout(queryTimeout);
//        datasource.setLoginTimeout(loginTimeout);
//        datasource.setTimeBetweenEvictionRunsMillis(60000);
//        datasource.setMinEvictableIdleTimeMillis(3600000);
//        datasource.setValidationQuery("SELECT 1 FROM DUAL");
//        datasource.setTestWhileIdle(true);
//        datasource.setTestOnBorrow(false);
//
//        try {
//            datasource.setFilters(filters);
//        } catch (SQLException e) {
//            log.error("druid conf iguration initialization filter error",
//                    Throwables.getStackTraceAsString(e));
//        }
//        System.out.println("成功");
//        return datasource;
//    }
// }
