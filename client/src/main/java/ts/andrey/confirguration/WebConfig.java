package ts.andrey.confirguration;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public ServletListenerRegistrationBean<SessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(new SessionListener());
    }

}
