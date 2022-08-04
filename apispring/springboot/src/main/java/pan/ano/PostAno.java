package pan.ano;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * @Author pan
 * @Date 2022/8/4 10:34
 * @Version 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface PostAno {
    String value() default "";
}