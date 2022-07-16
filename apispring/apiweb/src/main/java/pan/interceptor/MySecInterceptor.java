package pan.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MySecInterceptor implements HandlerInterceptor {
    @Override
    //当处理器方法执行之前调用
    //返回值: true  放行   false  不放行    就执行不了处理器方法
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle当处理器方法执行之前调用2");
        return true;//设置为true才能放行
    }

    @Override
    //当处理器方法执行之后会自动调用调用
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle当处理器方法执行之后会自动调用调用2");
    }

    @Override
    //请求处理完毕之后, 会调用
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion请求处理完毕之后会调用2");
    }
}
