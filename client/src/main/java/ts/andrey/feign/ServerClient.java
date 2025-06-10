package ts.andrey.feign;

import org.springframework.cloud.openfeign.FeignClient;
import ts.andrey.api.ApiApi;

@FeignClient(
        value = "server",
        url = "${server-back.url}"
)
public interface ServerClient extends ApiApi {

}
