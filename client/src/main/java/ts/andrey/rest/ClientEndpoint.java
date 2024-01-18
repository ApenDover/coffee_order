package ts.andrey.rest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientEndpoint {

    public static final String MAIN_PAGE = "/";

    public static final String REDIRECT_TO_HOME = "redirect:" + ClientEndpoint.MAIN_PAGE;

    public static final String BARISTA_PAGE = "/barista";

    public static final String NEW_ORDER = "/newOrder";

    public static final String CLOSE_ORDER = "/closeOrder/{id}";
    public static final String SET_MILK = "/milk/{id}";

    public static final String SET_SYRUP = "/syrup/{id}";

    public static final String SET_DRINK = "/drink/{id}";

    public static final String SET_DESSERT = "/dessert/{id}";

}
