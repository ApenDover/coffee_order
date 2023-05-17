package ts.andrey.confirguration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import ts.andrey.confirguration.property.RestProperties;
import ts.andrey.confirguration.property.SslProperties;

import java.util.Optional;

@Configuration
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties(RestProperties.class)
public class RestTemplateConfiguration {

    @Bean
    HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory(RestProperties restProperties)
            throws Exception {

        final var httpClient = customSslContextRequired(restProperties.getSsl())
                ? createClientWithCustomSslContext(restProperties.getSsl()) : HttpClientBuilder.create().build();

        final var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        Optional.ofNullable(restProperties.getReadTimeout()).ifPresent(requestFactory::setReadTimeout);
        Optional.ofNullable(restProperties.getConnectionTimeout()).ifPresent(requestFactory::setConnectTimeout);
        return requestFactory;
    }

    private boolean customSslContextRequired(SslProperties sslProperties) {
        return StringUtils.hasText(sslProperties.getKeyStore())
                && sslProperties.getKeyStorePassword() != null
                && sslProperties.getKeyStorePassword().length > 0;
    }

    private HttpClient createClientWithCustomSslContext(SslProperties sslProperties) throws Exception {
        final var sslContext = SSLContextBuilder
                .create()
                .loadKeyMaterial(ResourceUtils.getFile(sslProperties.getKeyStore()),
                        sslProperties.getStorePassword(), sslProperties.getKeyStorePassword())
                .setProtocol(sslProperties.getProtocol()).build();

        return HttpClientBuilder.create()
                .setSSLContext(sslContext)
                .build();
    }

    @Bean
    RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory) {
        return new RestTemplate(httpComponentsClientHttpRequestFactory);
    }

}
