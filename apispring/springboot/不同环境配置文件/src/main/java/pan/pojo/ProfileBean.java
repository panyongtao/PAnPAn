package pan.pojo;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author pan
 * @Date 2022/7/19 13:41
 * @Version 1.0
 */
@Component
@ToString
public class ProfileBean {
    @Value("${active}")
    private String val;
}
