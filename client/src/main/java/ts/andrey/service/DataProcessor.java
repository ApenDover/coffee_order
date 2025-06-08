package ts.andrey.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ts.andrey.common.dto.OrderingDTO;
import ts.andrey.constants.CoffeeRestConst;
import ts.andrey.data.Dessert;
import ts.andrey.data.Drink;
import ts.andrey.data.Milk;
import ts.andrey.data.NewOrderCreate;
import ts.andrey.data.Syrup;
import ts.andrey.dto.InOutOrderingDTOView;
import ts.andrey.mapper.OrderingToOrderingDtoMapper;
import ts.andrey.rest.ClientEndpoint;
import ts.andrey.rest.UserSession;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataProcessor {

    private final GetApi getApi;

    private final CoffeeRestConst coffeeRestConst;

    private final OrderingToOrderingDtoMapper mapper;

    private final List<Syrup> syrupArrayList;

    private final List<Dessert> dessertArrayList;

    private final List<Drink> drinkArrayList;

    private final List<Drink> coffeeDrinkArrayList;

    private final List<Drink> otherDrinkArrayList;

    private final List<Milk> milkArrayList;

    private final List<InOutOrderingDTOView> orderingArrayList;

    private final List<InOutOrderingDTOView> orderingTrueArrayList;

    private final List<InOutOrderingDTOView> orderingFalseArrayList;

    private final Set<String> coffeeName;

    private final Set<String> otherDrinkName;

    private final HashMap<String, TreeSet<Drink>> otherDrinkHashMap;

    private final HashMap<String, TreeSet<Drink>> coffeeDrinkHashMap;

    private final List<Dessert> dessertList;

    public void getMenu(Model model, HttpSession session) {

        updateData();

        final var userSession = (UserSession) session.getAttribute("userSession");

        model.addAttribute("milkList", milkArrayList);
        model.addAttribute("syrupList", syrupArrayList);
        model.addAttribute("coffeeDrinkHashMap", coffeeDrinkHashMap);
        model.addAttribute("otherDrinkHashMap", otherDrinkHashMap);
        model.addAttribute("dessertList", dessertArrayList);
        model.addAttribute("cafeOrder", userSession.getCafeOrder());
        model.addAttribute("setDrinkLink", ClientEndpoint.SET_DRINK);
        model.addAttribute("setMilkLink", ClientEndpoint.SET_MILK);
        model.addAttribute("setSyrupLink", ClientEndpoint.SET_SYRUP);
        model.addAttribute("setDessertLink", ClientEndpoint.SET_DESSERT);
        model.addAttribute("newOrderLink", ClientEndpoint.NEW_ORDER);

    }

    public void setDrink(int id, UserSession userSession) {
        final var drink = drinkArrayList.stream()
                .filter(drink1 -> drink1.getId() == id)
                .findFirst()
                .orElseThrow();
        final var ordering = userSession.getCafeOrder();
        if (ordering.getDrink() == null) {
            ordering.setDrink(drink);
            int price = ordering.getPrice();
            ordering.setPrice(price + drink.getPrice());
        } else {
            int price = ordering.getPrice();
            final var beforeDrink = ordering.getDrink();
            if (beforeDrink.equals(drink)) {
                ordering.setPrice(price - ordering.getDrink().getPrice());
                ordering.setDrink(null);
            } else {
                ordering.setPrice(price - beforeDrink.getPrice() + drink.getPrice());
                ordering.setDrink(drink);
            }
        }
    }

    public void setMilk(int id, UserSession userSession) {
        final var ordering = userSession.getCafeOrder();
        final var milk = milkArrayList.stream().filter(milk1 -> milk1.getId() == id).findFirst().orElse(new Milk());
        if (ordering.getMilk() == null) {
            ordering.setMilk(milk);
            int price = ordering.getPrice();
            ordering.setPrice(price + milk.getPrice());
        } else {
            final var beforeMilk = ordering.getMilk();
            int price = ordering.getPrice();
            if (beforeMilk.equals(milk)) {
                ordering.setPrice(price - ordering.getMilk().getPrice());
                ordering.setMilk(null);
            } else {
                ordering.setPrice(price - beforeMilk.getPrice() + milk.getPrice());
                ordering.setMilk(milk);
            }
        }
    }

    public void setSyrup(int id, UserSession userSession) {
        final var ordering = userSession.getCafeOrder();
        final var syrup = syrupArrayList.stream().filter(syrup1 -> syrup1.getId() == id).findFirst().orElse(new Syrup());
        if (ordering.getSyrup() == null) {
            ordering.setSyrup(syrup);
            int price = ordering.getPrice();
            ordering.setPrice(price + syrup.getPrice());
        } else {
            final var beforeSyrup = ordering.getSyrup();
            int price = ordering.getPrice();
            if (beforeSyrup.equals(syrup)) {
                ordering.setPrice(price - ordering.getSyrup().getPrice());
                ordering.setSyrup(null);
            } else {
                ordering.setPrice(price - beforeSyrup.getPrice() + syrup.getPrice());
                ordering.setSyrup(syrup);
            }
        }
    }

    public void setDessert(int id, UserSession userSession) {
        final var ordering = userSession.getCafeOrder();
        final var dessert = dessertArrayList.stream().filter(dessert1 -> dessert1.getId() == id).findFirst().orElse(new Dessert());
        if (dessertList.contains(dessert)) {
            dessertList.remove(dessert);
            int price = ordering.getPrice();
            ordering.setPrice(price - dessert.getPrice());
        } else {
            dessertList.add(dessert);
            int price = ordering.getPrice();
            ordering.setPrice(price + dessert.getPrice());
        }
        ordering.setDesserts(dessertList);
    }

    public void newOrder(Model model, String comment, UserSession userSession) {
        final var ordering = userSession.getCafeOrder();
        ordering.setComment(comment);
        final var orderRequest = mapper.toOrderingDTO(ordering);
        final var order = getApi.sendOrder(coffeeRestConst.getNewOrderEndPoint(), orderRequest);
        model.addAttribute("orderID", order);
        try {
            model.addAttribute("cafeOrder", ordering.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("mainPageLink", ClientEndpoint.MAIN_PAGE);
        ordering.clear();
    }

    private void updateData() {
        try {
            if (syrupArrayList.isEmpty()) {
                syrupArrayList.addAll(getApi.getObjectList(coffeeRestConst.getAllSyrupEndPoint(), Syrup[].class));
            }
            if (dessertArrayList.isEmpty()) {
                dessertArrayList.addAll(getApi.getObjectList(coffeeRestConst.getAllDesertsEndPoint(), Dessert[].class));
            }
            if (drinkArrayList.isEmpty()) {
                drinkArrayList.addAll(getApi.getObjectList(coffeeRestConst.getAllDrinkEndPoint(), Drink[].class));
            }
            if (milkArrayList.isEmpty()) {
                milkArrayList.addAll(getApi.getObjectList(coffeeRestConst.getAllMilkEndPoint(), Milk[].class));
            }
            if (coffeeDrinkArrayList.isEmpty()) {
                coffeeDrinkArrayList.addAll(drinkArrayList.stream()
                        .filter(Drink::getIsCoffee)
                        .toList());
            }
            if (otherDrinkArrayList.isEmpty()) {
                otherDrinkArrayList.addAll(drinkArrayList);
                otherDrinkArrayList.removeAll(coffeeDrinkArrayList);
            }
            if (coffeeName.isEmpty()) {
                coffeeDrinkArrayList.forEach(drink -> coffeeName.add(drink.getName()));
            }
            if (otherDrinkName.isEmpty()) {
                otherDrinkArrayList.forEach(drink -> otherDrinkName.add(drink.getName()));
            }
            if (coffeeDrinkHashMap.isEmpty()) {
                extractFromSet(coffeeName, coffeeDrinkArrayList, coffeeDrinkHashMap);
            }
            if (otherDrinkHashMap.isEmpty()) {
                extractFromSet(otherDrinkName, otherDrinkArrayList, otherDrinkHashMap);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void extractFromSet(Set<String> drink,
                                List<Drink> drinkList,
                                HashMap<String, TreeSet<Drink>> drinkHashMap) {
        drink.forEach(drinkHead -> {
            final var coffeeSet = new TreeSet<>(drinkList.stream().filter(d -> d.getName().equals(drinkHead)).toList());
            drinkHashMap.put(drinkHead, coffeeSet);
        });
    }

    public void getBarista(Model model) {
        if (orderingArrayList.isEmpty()) {
            loadData();
        } else {
            final var newOrderCreate = getApi.getObjectList(coffeeRestConst.getUpdateInfoEndPoint(), NewOrderCreate[].class).get(0);
            if (newOrderCreate.isUpdate()) {
                loadData();
                getApi.getObjectList(coffeeRestConst.getMakeUpdateFalseEndPoint(), String[].class);
            }
        }

        Collections.sort(orderingArrayList);
        Collections.sort(orderingTrueArrayList);
        Collections.reverse(orderingTrueArrayList);
        Collections.sort(orderingFalseArrayList);
        Collections.reverse(orderingFalseArrayList);

        model.addAttribute("orderList", orderingArrayList);
        model.addAttribute("orderTrueList", orderingTrueArrayList);
        model.addAttribute("orderFalseList", orderingFalseArrayList);
        model.addAttribute("closeOrderLink", ClientEndpoint.CLOSE_ORDER);
    }

    public void closeOrder(int id) {
        getApi.sendOrder(coffeeRestConst.getCloseOrderEndPoint(), new OrderingDTO().setOrderId(id));
    }

    private void loadData() {
        orderingArrayList.clear();
        orderingTrueArrayList.clear();
        orderingFalseArrayList.clear();
        orderingArrayList.addAll(getApi.getObjectList(coffeeRestConst.getAllTodayOrderEndPoint(), InOutOrderingDTOView[].class));
        for (InOutOrderingDTOView orderingDTO : orderingArrayList) {
            if (orderingDTO.isStatus()) {
                orderingTrueArrayList.add(orderingDTO);
            } else {
                orderingFalseArrayList.add(orderingDTO);
            }
        }
    }

}
