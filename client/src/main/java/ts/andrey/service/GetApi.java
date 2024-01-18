package ts.andrey.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ts.andrey.common.dto.OrderingDTO;

import java.util.List;
import java.util.Optional;

@Component
public class GetApi extends AbstractClientService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public GetApi(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public Object sendOrder(String baseUrl, OrderingDTO orderingDTO) {
        try {
            return sendPost(baseUrl, OBJECT_MAPPER.writeValueAsString(orderingDTO), Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<String> getPassword(String url, String userName) {
        return Optional.ofNullable(sendGet(url, userName, String.class));
    }

    public <T> List<T> getObjectList(String url, Class<T[]> clazz) {
        final String result;
        final T[] objArray;
        try {
            result = GetRequest.getHTML(url);
            objArray = OBJECT_MAPPER.readValue(result, clazz);
            return List.of(objArray);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
