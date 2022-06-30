package com.example;

import com.ejlchina.json.JSONKit;
import com.ejlchina.searcher.BeanMeta;
import com.ejlchina.searcher.ParamFilter;
import com.ejlchina.searcher.dialect.Dialect;
import com.ejlchina.searcher.dialect.OracleDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * 方言默认是mysql
 */
@SpringBootApplication
public class Application implements WebMvcConfigurer {

    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
    //oracle下使用的话需要定义方言
    public Dialect oracleDialet(){
        return new OracleDialect();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
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


}
