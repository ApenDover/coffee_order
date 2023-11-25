package ts.andrey.constants;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
        allMilkEndPoint = url + "/api/getAllMilk";
        allSyrupEndPoint = url + "/api/getAllSyrup";
        allDesertsEndPoint = url + "/api/getAllDessert";
        allDrinkEndPoint = url + "/api/getAllDrink";
        allTodayOrderEndPoint = url + "/api/getAllTodayOrders";
        newOrderEndPoint = url + "/api/newOrder";
        closeOrderEndPoint = url + "/api/closeOrder";
        updateInfoEndPoint = url + "/api/getUpdateInfo";
        makeUpdateFalseEndPoint = url + "/api/makeUpdateFalse";
    }

}
