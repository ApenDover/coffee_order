package ts.andrey.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ts.andrey.common.data.entity.Dessert;
import ts.andrey.common.data.entity.Drink;
import ts.andrey.common.data.entity.Milk;
import ts.andrey.common.data.entity.NewOrderCreate;
import ts.andrey.common.data.entity.Ordering;
import ts.andrey.common.data.entity.Syrup;
import ts.andrey.common.dto.OrderingDTO;
import ts.andrey.constants.CoffeeRestConst;
import ts.andrey.dto.InOutOrderingDTOView;
import ts.andrey.mapper.OrderingToOrderingDtoMapper;
import ts.andrey.rest.ClientEndpoint;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

    private final Ordering ordering = new Ordering();

    private final List<Dessert> dessertList;

    public void getMenu(Model model) {

        if (drinkArrayList.isEmpty()) {
            updateData();
        }
        model.addAttribute("milkList", milkArrayList);
        model.addAttribute("syrupList", syrupArrayList);
        model.addAttribute("otherDrinkHashMap", otherDrinkHashMap);
        model.addAttribute("coffeeDrinkHashMap", coffeeDrinkHashMap);
        model.addAttribute("dessertList", dessertArrayList);
        model.addAttribute("order", ordering);
        model.addAttribute("setDrinkLink", ClientEndpoint.SET_DRINK);
        model.addAttribute("setMilkLink", ClientEndpoint.SET_MILK);
        model.addAttribute("setSyrupLink", ClientEndpoint.SET_SYRUP);
        model.addAttribute("setDessertLink", ClientEndpoint.SET_DESSERT);
        model.addAttribute("newOrderLink", ClientEndpoint.NEW_ORDER);

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

    public void newOrder(Model model, String comment) {
        ordering.setComment(comment);
        model.addAttribute("orderID", getApi.sendOrder(coffeeRestConst.getNewOrderEndPoint(),
                mapper.toOrderingDTO(ordering)));
        try {
            model.addAttribute("order", ordering.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        model.addAttribute("mainPageLink", ClientEndpoint.MAIN_PAGE);
        ordering.clear();
    }

    public void closeOrder(int id) {
        getApi.sendOrder(coffeeRestConst.getCloseOrderEndPoint(), new OrderingDTO().setOrderId(id));
    }

    public void setMilk(int id) {
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

    public void setSyrup(int id) {
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

    public void setDrink(int id) {
        Drink drink = drinkArrayList.stream().filter(drink1 -> drink1.getId() == id).findFirst().orElse(new Drink());
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

    public void setDessert(int id) {
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

    private void updateData() {
        try {
            syrupArrayList.addAll(getApi.getObjectList(coffeeRestConst.getAllSyrupEndPoint(), Syrup[].class));
            dessertArrayList.addAll(getApi.getObjectList(coffeeRestConst.getAllDesertsEndPoint(), Dessert[].class));
            drinkArrayList.addAll(getApi.getObjectList(coffeeRestConst.getAllDrinkEndPoint(), Drink[].class));
            milkArrayList.addAll(getApi.getObjectList(coffeeRestConst.getAllMilkEndPoint(), Milk[].class));
            coffeeDrinkArrayList.addAll(drinkArrayList.stream().filter(Drink::isCoffee).collect(Collectors.toList()));
            otherDrinkArrayList.addAll(drinkArrayList);
            otherDrinkArrayList.removeAll(coffeeDrinkArrayList);

            coffeeDrinkArrayList.forEach(drink -> coffeeName.add(drink.getName()));
            otherDrinkArrayList.forEach(drink -> otherDrinkName.add(drink.getName()));

            extractFromSet(coffeeName, coffeeDrinkArrayList, coffeeDrinkHashMap);
            extractFromSet(otherDrinkName, otherDrinkArrayList, otherDrinkHashMap);

        } catch (Exception e) {
            log.error(e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void extractFromSet(Set<String> drink,
                                List<Drink> drinkList,
                                HashMap<String, TreeSet<Drink>> drinkHashMap) {
        drink.forEach(s -> {
            final var coffeeSet = new TreeSet<Drink>();
            for (Drink d : drinkList) {
                if (d.getName().equals(s)) {
                    coffeeSet.add(d);
                }
            }
            drinkHashMap.put(s, coffeeSet);
        });
    }

}
