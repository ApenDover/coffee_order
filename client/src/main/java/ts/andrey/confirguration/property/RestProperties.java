package ts.andrey.confirguration.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("rest")
@Data
public class RestProperties {

    private SslProperties ssl = new SslProperties();

    private Integer connectionTimeout = 30_000;

    private Integer readTimeout = 30_000;

}
