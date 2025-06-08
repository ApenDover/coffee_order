package ts.andrey.common.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ServerEndpoint {

    public static final String API = "/api";

    public static final String GET_ALL_TODAY_ORDERS = "/getAllTodayOrders";

    public static final String GET_ALL_ORDERS = "/getAllOrders";

    public static final String GET_UPDATE_INFO = "/getUpdateInfo";

    public static final String MAKE_UPDATE_FALSE = "/makeUpdateFalse";

    public static final String GET_ORDER_ID = "/getOrder/{id}";

    public static final String MILK_LIST = "/getAllMilk";

    public static final String SYRUP_LIST = "/getAllSyrup";

    public static final String DESSERT_LIST = "/getAllDessert";

    public static final String DRINK_LIST = "/getAllDrink";

    public static final String NEW_ORDER = "/newOrder";

    public static final String CLOSE_ORDER = "/closeOrder";

    public static final String NEW_MILK = "/newMilk";

    public static final String NEW_DRINK = "/newDrink";

    public static final String NEW_SYRUP = "/newSyrup";

    public static final String NEW_DESSERT = "/newDessert";

    public static final String PASSWORD = "/getPassword/{user}";

}
