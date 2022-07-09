package pan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "jdbc")
@Data
public class MyProperties {
    private String url;

    private String driverClassName;

    private String username;

    private String password;

    private String[] arrayPros;

    private List<String> listPros;

    private Map<String,String> mapPros;

    private List<Map<String,String>> listMapPros;

}
