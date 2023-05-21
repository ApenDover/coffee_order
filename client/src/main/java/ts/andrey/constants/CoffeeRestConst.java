package ts.andrey.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class CoffeeRestConst {

    @Getter
    private static String closeOrderEndPoint;

    @Getter
    private static String allMilkEndPoint;

    @Getter
    private static String allSyrupEndPoint;

    @Getter
    private static String allDesertsEndPoint;

    @Getter
    private static String allDrinkEndPoint;

    @Getter
    private static String allOrderEndPoint;

    @Getter
    private static String newOrderEndPoint;

    @Getter
    private static String updateInfoEndPoint;

    @Getter
    private static String makeUpdateFalseEndPoint;

    @Autowired
    private CoffeeRestConst(@Value("${serverAddress}") String url) {
        allMilkEndPoint = url + "/api/getAllMilk";
        allSyrupEndPoint = url + "/api/getAllSyrup";
        allDesertsEndPoint = url + "/api/getAllDessert";
        allDrinkEndPoint = url + "/api/getAllDrink";
        allOrderEndPoint = url + "/api/getAllOrders";
        newOrderEndPoint = url + "/api/newOrder";
        closeOrderEndPoint = url + "/api/closeOrder";
        updateInfoEndPoint = url + "/api/getUpdateInfo";
        makeUpdateFalseEndPoint = url + "/api/makeUpdateFalse";
    }

}