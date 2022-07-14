package com.pan.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 在使用token校验时，会话校验基本没有存在的校验
 * 使用token时也能支持手机端校验
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class SessionConfig {
    @Bean
    public DefaultCookieSerializer defaultCookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer() ;
        defaultCookieSerializer.setCookieName("PAN") ;
        defaultCookieSerializer.setCookiePath("/");
        return defaultCookieSerializer ;
    }

}
