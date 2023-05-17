package ts.andrey.confirguration.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "signature")
public class SignatureProperties {

    private String privateKeyPath;

    private Long cacheExpirationTimeout = 300L;

    private Integer cacheMaxSize = 100;

}
