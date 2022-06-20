package com.pan.aspect;

import com.pan.annotation.ParamInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class PrintParamAspect {
    @Pointcut("@annotation(com.pan.annotation.ParamInfo) || @within(com.pan.annotation.ParamInfo)")
    public void pointCut() {

    }

    @Before("pointCut()")
    public void doBefore(JoinPoint pjp) {
        try {
            StringBuilder stringBuffer = new StringBuilder();
            //得到参数对象
            Object[] args = pjp.getArgs();
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method method = methodSignature.getMethod();
            //请求的方法参数名称
            LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
            String[] paramNames = discoverer.getParameterNames(method);
            if (paramNames != null) {
                for (int i = 0; i < args.length; i++) {
                    stringBuffer.append("").append(paramNames[i]).append(": ").append(args[i]);
                }
            }
            try {
                MethodSignature signature = (MethodSignature) pjp.getSignature();
                String value = "";
                try {
                    ParamInfo annotation = signature.getMethod().getAnnotation(ParamInfo.class);
                    value = annotation.value();
                } catch (Exception ignore) {

                }
                if (StringUtils.isEmpty(value)) {
                    value = signature.getMethod().getName();
                }
                log.info("方法"+value + "--" + stringBuffer.toString());
            } catch (Throwable throwable) {
                log.error("获取ParamInfo注解代码执行异常", throwable);
            }
        } catch (Throwable throwable) {
            log.error("打印参数获取日志信息异常", throwable);
        }
    }
}

