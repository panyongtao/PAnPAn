package com.pan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.pan.dao","com.pan.mapper"})  //1
@EnableScheduling
@ImportResource(locations = {"classpath:thread-pool.xml"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    /**
     * 只是针对url路径添加前后缀
     * @param dispatcherServlet
     * @return
     */
    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet){
        ServletRegistrationBean reg = new ServletRegistrationBean(dispatcherServlet) ;
        reg.getUrlMappings().clear ();
        reg.addUrlMappings("/services/*");
        reg.addUrlMappings("/*") ;
        return reg;
    }
//    @EnableOAuth2Sso
//    @Component
//    public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//        @Resource
//        private McFilterSecurityInterceptor mcFilterSecurityInterceptor ;
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.headers().frameOptions().disable ();
//            http.addFilterBefore(mcFilterSecurityInterceptor,FilterSecurityInterceptor.class);
//            http. authorizeRequests(). antMatchers(....antPatterns: " /services/v1/*”).permitAll().
//            antMatchers( ....antPatterns: "/**") . authenticated() ;
//            http. authorizeRequests(). antMatchers("/4NK"").permitAll();
//        }
//
//    }
}
