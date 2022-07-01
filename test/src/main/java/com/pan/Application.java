package com.pan;

import com.ejlchina.json.JSONKit;
import com.ejlchina.searcher.BeanMeta;
import com.ejlchina.searcher.ParamFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@MapperScan(basePackages = {"com.pan.dao","com.pan.mapper"})  //1
@EnableScheduling
@ImportResource(locations = {"classpath:thread-pool.xml"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 为了简化多值参数传递
     * 参考：https://github.com/ejlchina/bean-searcher/issues/10
     * 用 age=[20,30] 替代 age-0=20&age-1=30 #10
     * @return 参数过滤器
     */
    @Bean
    public ParamFilter myParamFilter() {
        return new ParamFilter() {
            final String OP_SUFFIX = "-op";
            @Override
            public <T> Map<String, Object> doFilter(BeanMeta<T> beanMeta, Map<String, Object> paraMap) {
                Map<String, Object> newParaMap = new HashMap<>();
                paraMap.forEach((key, value) -> {
                    if (key == null) {
                        return;
                    }
                    boolean isOpKey = key.endsWith(OP_SUFFIX);
                    String opKey = isOpKey ? key : key + OP_SUFFIX;
                    Object opVal = paraMap.get(opKey);
                    if (!"mv".equals(opVal) && !"bt".equals(opVal)) {
                        newParaMap.put(key, value);
                        return;
                    }
                    if (newParaMap.containsKey(key)) {
                        return;
                    }
                    String valKey = key;
                    Object valVal = value;
                    if (isOpKey) {
                        valKey = key.substring(0, key.length() - OP_SUFFIX.length());
                        valVal = paraMap.get(valKey);
                    }
                    if (likelyJsonArr(valVal)) {
                        try {
                            String vKey = valKey;
                            JSONKit.toArray((String) valVal).forEach(
                                    (index, data) -> newParaMap.put(vKey + "-" + index, data.toString())
                            );
                            newParaMap.put(opKey, opVal);
                            return;
                        } catch (Exception ignore) {}
                    }
                    newParaMap.put(key, value);
                });
                return newParaMap;
            }

            private boolean likelyJsonArr(Object value) {
                if (value instanceof String) {
                    String str = ((String) value).trim();
                    return str.startsWith("[") && str.endsWith("]");
                }
                return false;
            }

        };
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
