package ts.andrey.constants;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ts.andrey.common.rest.ServerEndpoint;

@Component
@Getter
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public final class CoffeeRestConst {

    private String closeOrderEndPoint;

    private String allMilkEndPoint;

    private String allSyrupEndPoint;

    private String allDesertsEndPoint;

    private String allDrinkEndPoint;

    private String allTodayOrderEndPoint;

    private String newOrderEndPoint;

    private String updateInfoEndPoint;

    private String makeUpdateFalseEndPoint;

    @Value("${server-back.address}")
    private String url;

    @PostConstruct
    private void init() {
        allMilkEndPoint = url + ServerEndpoint.API + ServerEndpoint.MILK_LIST;
        allSyrupEndPoint = url + ServerEndpoint.API + ServerEndpoint.SYRUP_LIST;
        allDesertsEndPoint = url + ServerEndpoint.API + ServerEndpoint.DESSERT_LIST;
        allDrinkEndPoint = url + ServerEndpoint.API + ServerEndpoint.DRINK_LIST;
        allTodayOrderEndPoint = url + ServerEndpoint.API + ServerEndpoint.ALL_TODAY_ORDERS;
        newOrderEndPoint = url + ServerEndpoint.API + ServerEndpoint.NEW_ORDER;
        closeOrderEndPoint = url + ServerEndpoint.API + ServerEndpoint.CLOSE_ORDER;
        updateInfoEndPoint = url + ServerEndpoint.API + ServerEndpoint.UPDATE_INFO;
        makeUpdateFalseEndPoint = url + ServerEndpoint.API + ServerEndpoint.MAKE_UPDATE_FALSE;
    }

}
