package pan.template;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.boot.autoconfigure.solr.SolrProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Service;

/**
 * @Configuration,@Service,@Component都可以在类内部使用@Bean
 * @Bean 方法也可以在用 @Configuration注释的类中声明。
 * 例如，可以在 @Component 类中甚至在其他类中将bean方法声明为。
 * 在这种情况下， @Bean方法将以所谓的lite模式进行处理。
 * bean 容器中将 lite模式下的 Bean方法视为普通的工厂方法（类似于XML中的 factory-method声明），适当地应用范围和生命周期回调。在这种情况下，包含的类保持不变，并且对于包含类或工厂方法没有异常约束。
 * 与 @Configuration类中bean方法的语义相反，在lite模式下不支持 bean间引用 。相反，当一个 @Bean方法以lite模式调用另一个@Bean方法时，该调用是标准的Java方法调用；Spring不会通过CGLIB代理拦截调用。这类似于内部 @Transactional方法调用，然而在代理模式下，Spring不会拦截调用；但是仅在AspectJ模式下，Spring会拦截调用。如下
 */
//@Configuration
//@Component
@Service
public class SolrTemplateConfig {
    @Bean
    public HttpSolrClient solrClient(SolrProperties solrProperties) {
        HttpClient httpClient = new SystemDefaultHttpClient();
        return new HttpSolrClient.Builder(solrProperties.getHost()).withHttpClient(httpClient).build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient solrClient) {
        System.out.println(11);
        return new SolrTemplate(solrClient);
    }

}

