package ts.andrey.config;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
public abstract class IntegrationTest {

    public static final String BASE_URL = "http://localhost:";
    public static final String ACTUATOR_PROMETHEUS_URL = "/actuator/prometheus";
    public static final String ACTUATOR_INFO_URL = "/actuator/info";
    public static final String SLASH = "/";

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");
    @LocalServerPort
    public transient int localPort;
    @Autowired
    public EntityManager entityManager;
    @Autowired
    public WebApplicationContext context;
    @Autowired
    public TestRestTemplate restTemplate;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    public String getUrl(String path) {
        if (Objects.isNull(path) || !path.startsWith(SLASH)) {
            path = SLASH + path;
        }
        return BASE_URL + localPort + SLASH + path;
    }

    public String getHttpUnsecuredUrl(String path) {
        String url = BASE_URL + localPort + path;
        assertTrue(url.contains("http:"));
        return url;
    }

    public ResponseEntity<String> sendGet(String path) {
        return restTemplate.getForEntity(getUrl(path), String.class);
    }

    public ResponseEntity<Object> sendPost(String path, Object object) {
        return restTemplate.postForEntity(getUrl(path), object, Object.class);
    }

    public <Req, Res> ResponseEntity<Res> sendPost(String path, Req request, Class<Res> responseClass) {
        return restTemplate.postForEntity(getUrl(path), request, responseClass);
    }

    public <Req, Res> Res sendPostOk(String url, Req request, Class<Res> responseClass) {
        return sendPostOk(url, new HttpEntity<>(request), responseClass);
    }

    public <Res> Res sendPostOk(String url, HttpEntity<?> httpEntity, Class<Res> responseClass) {
        final var actual = sendPost(url, httpEntity, responseClass);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        final var body = actual.getBody();
        assertInstanceOf(responseClass, body);
        return body;
    }

    public ResponseEntity<String> sendGetHttpUnsecured(String path) {
        String httpUnsecuredUrl = getHttpUnsecuredUrl(path);
        return restTemplate.getForEntity(httpUnsecuredUrl, String.class);
    }

}
