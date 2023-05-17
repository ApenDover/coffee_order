package ts.andrey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@RequiredArgsConstructor
public abstract class AbstractClientService {

    private final RestTemplate restTemplate;

    protected <T> T send(String url, String body, Class<T> returnType) {
        final var entity = generateEntity(body);
        final var response = restTemplate.exchange(url, HttpMethod.POST, entity, returnType);
        return response.getBody();
    }

    private HttpEntity<?> generateEntity(Object body) {
        final var header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(body, header);
    }

}

