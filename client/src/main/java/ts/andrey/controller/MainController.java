package ts.andrey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ts.andrey.constants.CoffeeRestConst;
import ts.andrey.dto.InOrderingDTO;
import ts.andrey.dto.OrderingDTO;
import ts.andrey.mapper.OrderingToOrderDtoMapper;
import ts.andrey.models.Dessert;
import ts.andrey.models.Drink;
import ts.andrey.models.Milk;
import ts.andrey.models.NewOrderCreate;
import ts.andrey.models.Ordering;
import ts.andrey.models.Syrup;
import ts.andrey.service.GetApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final GetApi getApi;
    private final List<Syrup> syrupArrayList;
    private final ArrayList<Dessert> dessertArrayList;
    private final ArrayList<Drink> drinkArrayList;
    private final ArrayList<Drink> coffeeDrinkArrayList;
    private final ArrayList<Drink> otherDrinkArrayList;
    private final TreeSet<String> coffeeName;
    private final TreeSet<String> otherDrinkName;
    private final HashMap<String, TreeSet<Drink>> otherDrinkHashMap;
    private final HashMap<String, TreeSet<Drink>> coffeeDrinkHashMap;
    private final ArrayList<Milk> milkArrayList;
    private final ArrayList<InOrderingDTO> orderingArrayList;
    private final ArrayList<InOrderingDTO> orderingTrueArrayList;
    private final ArrayList<InOrderingDTO> orderingFalseArrayList;
    private final Ordering ordering = new Ordering();
    private final List<Dessert> dessertList;

    @GetMapping("/")
    public String index(Model model) {

        if (drinkArrayList.isEmpty()) {
            updateData();
        }

        model.addAttribute("milkList", milkArrayList);
        model.addAttribute("syrupList", syrupArrayList);
        model.addAttribute("otherDrinkHashMap", otherDrinkHashMap);
        model.addAttribute("coffeeDrinkHashMap", coffeeDrinkHashMap);
        model.addAttribute("dessertList", dessertArrayList);
        model.addAttribute("order", ordering);
        return "menu";
    }

    @GetMapping("/barista")
    public String barista(Model model) {

        if (orderingArrayList.isEmpty()) {
            loadData();
        } else {
            NewOrderCreate newOrderCreate = getApi.getObjectList(CoffeeRestConst.getUpdateInfoEndPoint(), NewOrderCreate[].class).get(0);
            if (newOrderCreate.isUpdate()) {
                loadData();
                getApi.getObjectList(CoffeeRestConst.getMakeUpdateFalseEndPoint(), String[].class);
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

        return "barista";
    }

    @GetMapping("/newOrder")
    public String newOrder(Model model, @RequestParam("comment") String comment) throws CloneNotSupportedException {
        ordering.setComment(comment);
        model.addAttribute("orderID", getApi.sendOrder(CoffeeRestConst.getNewOrderEndPoint(),
                OrderingToOrderDtoMapper.INSTANCE.toOrderingDTO(ordering)));
        model.addAttribute("order", ordering.clone());
        return "orderCreate";
    }

    @GetMapping("/closeOrder/{id}")
    public String updateOrder(@PathVariable("id") int id) {
        getApi.sendOrder(CoffeeRestConst.getCloseOrderEndPoint(), new OrderingDTO().setOrderId(id));
        return "redirect:/barista";
    }

    @GetMapping("/milk/{id}")
    public String setMilk(@PathVariable("id") int id) {

        Milk milk = milkArrayList.stream().filter(milk1 -> milk1.getId() == id).findFirst().get();
        if (ordering.getMilk() == null) {
            ordering.setMilk(milk);
            int price = ordering.getPrice();
            ordering.setPrice(price + milk.getPrice());
        } else {
            Milk beforeMilk = ordering.getMilk();
            int price = ordering.getPrice();
            if (beforeMilk.equals(milk)) {
                ordering.setPrice(price - ordering.getMilk().getPrice());
                ordering.setMilk(null);
            } else {
                ordering.setPrice(price - beforeMilk.getPrice() + milk.getPrice());
                ordering.setMilk(milk);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/syrup/{id}")
    public String setSyrup(@PathVariable("id") int id) {

        Syrup syrup = syrupArrayList.stream().filter(syrup1 -> syrup1.getId() == id).findFirst().get();
        if (ordering.getSyrup() == null) {
            ordering.setSyrup(syrup);
            int price = ordering.getPrice();
            ordering.setPrice(price + syrup.getPrice());
        } else {
            Syrup beforeSyrup = ordering.getSyrup();
            int price = ordering.getPrice();
            if (beforeSyrup.equals(syrup)) {
                ordering.setPrice(price - ordering.getSyrup().getPrice());
                ordering.setSyrup(null);
            } else {
                ordering.setPrice(price - beforeSyrup.getPrice() + syrup.getPrice());
                ordering.setSyrup(syrup);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/drink/{id}")
    public String setDrink(@PathVariable("id") int id) {
        Drink drink = drinkArrayList.stream().filter(drink1 -> drink1.getId() == id).findFirst().get();
        if (ordering.getDrink() == null) {
            ordering.setDrink(drink);
            int price = ordering.getPrice();
            ordering.setPrice(price + drink.getPrice());
        } else {
            int price = ordering.getPrice();
            Drink beforeDrink = ordering.getDrink();
            if (beforeDrink.equals(drink)) {
                ordering.setPrice(price - ordering.getDrink().getPrice());
                ordering.setDrink(null);
            } else {
                ordering.setPrice(price - beforeDrink.getPrice() + drink.getPrice());
                ordering.setDrink(drink);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/dessert/{id}")
    public String setDessert(@PathVariable("id") int id) {
        Dessert dessert = dessertArrayList.stream().filter(dessert1 -> dessert1.getId() == id).findFirst().get();
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
        return "redirect:/";
    }

    private void updateData() {
        try {
            syrupArrayList.addAll(getApi.getObjectList(CoffeeRestConst.getAllSyrupEndPoint(), Syrup[].class));
            dessertArrayList.addAll(getApi.getObjectList(CoffeeRestConst.getAllDesertsEndPoint(), Dessert[].class));
            drinkArrayList.addAll(getApi.getObjectList(CoffeeRestConst.getAllDrinkEndPoint(), Drink[].class));
            milkArrayList.addAll(getApi.getObjectList(CoffeeRestConst.getAllMilkEndPoint(), Milk[].class));
            coffeeDrinkArrayList.addAll(drinkArrayList.stream().filter(Drink::isCoffee).collect(Collectors.toList()));
            otherDrinkArrayList.addAll(drinkArrayList);
            otherDrinkArrayList.removeAll(coffeeDrinkArrayList);
            coffeeDrinkArrayList.forEach(drink -> {
                coffeeName.add(drink.getName());
            });

            otherDrinkArrayList.forEach(drink -> {
                otherDrinkName.add(drink.getName());
            });

            coffeeName.forEach(s -> {
                TreeSet<Drink> coffeeSet = new TreeSet<>();
                for (Drink drink : coffeeDrinkArrayList) {
                    if (drink.getName().equals(s)) {
                        coffeeSet.add(drink);
                    }
                }
                coffeeDrinkHashMap.put(s, coffeeSet);
            });

            otherDrinkName.forEach(s -> {
                TreeSet<Drink> otherDrinkSet = new TreeSet<>();
                for (Drink drink : otherDrinkArrayList) {
                    if (drink.getName().equals(s)) {
                        otherDrinkSet.add(drink);
                    }
                }
                otherDrinkHashMap.put(s, otherDrinkSet);
            });

        } catch (Exception e) {
            System.out.println(e.getMessage() + " " + Arrays.toString(e.getStackTrace()));
        }
    }

    private void loadData() {
        orderingArrayList.clear();
        orderingTrueArrayList.clear();
        orderingFalseArrayList.clear();
        orderingArrayList.addAll(getApi.getObjectList(CoffeeRestConst.getAllOrderEndPoint(), InOrderingDTO[].class));
        for (InOrderingDTO orderingDTO : orderingArrayList) {
            if (orderingDTO.isStatus()) {
                orderingTrueArrayList.add(orderingDTO);
            } else {
                orderingFalseArrayList.add(orderingDTO);
            }
        }
    }

}
