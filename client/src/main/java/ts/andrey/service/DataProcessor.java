package ts.andrey.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ts.andrey.mapper.CafeOrderClonnable;
import ts.andrey.mapper.OrderingToOrderingDtoMapper;
import ts.andrey.model.DessertDto;
import ts.andrey.model.DrinkDto;
import ts.andrey.model.InOutOrderingDTO;
import ts.andrey.model.MilkDto;
import ts.andrey.model.OrderingDTO;
import ts.andrey.model.SyrupDto;
import ts.andrey.rest.ClientEndpoint;
import ts.andrey.rest.UserSession;
import ts.andrey.utils.CafeOrderUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataProcessor {

    private final GetApi getApi;

    private final OrderingToOrderingDtoMapper orderingToOrderingDtoMapper;

    private final CafeOrderClonnable cafeOrderClonnable;

    private final List<SyrupDto> syrupLocalList;

    private final List<DessertDto> dessertLocalList;

    private final List<DrinkDto> drinkArrayList;

    private final List<DrinkDto> coffeeDrinkDtoArrayList;

    private final List<DrinkDto> otherDrinkDtoArrayList;

    private final List<MilkDto> milkArrayList;

    private final List<InOutOrderingDTO> orderingArrayList;

    private final List<InOutOrderingDTO> orderingTrueArrayList;

    private final List<InOutOrderingDTO> orderingFalseArrayList;

    private final Set<String> coffeeName = new HashSet<>();

    private final Set<String> otherDrinkDtoName = new HashSet<>();

    private final List<DessertDto> dessertList = new ArrayList<>();

    private final HashMap<String, TreeSet<DrinkDto>> otherDrinkDtoHashMap = new HashMap<>();

    private final HashMap<String, TreeSet<DrinkDto>> coffeeDrinkDtoHashMap = new HashMap<>();

    public void getMenu(Model model, HttpSession session) {

        updateData();

        final var userSession = (UserSession) session.getAttribute("userSession");

        model.addAttribute("milkList", milkArrayList);
        model.addAttribute("syrupList", syrupLocalList);
        model.addAttribute("coffeeDrinkHashMap", coffeeDrinkDtoHashMap);
        model.addAttribute("otherDrinkHashMap", otherDrinkDtoHashMap);
        model.addAttribute("dessertList", dessertLocalList);
        model.addAttribute("cafeOrder", userSession.getCafeOrderDto());
        model.addAttribute("setDrinkLink", ClientEndpoint.SET_DRINK);
        model.addAttribute("setMilkLink", ClientEndpoint.SET_MILK);
        model.addAttribute("setSyrupLink", ClientEndpoint.SET_SYRUP);
        model.addAttribute("setDessertLink", ClientEndpoint.SET_DESSERT);
        model.addAttribute("newOrderLink", ClientEndpoint.NEW_ORDER);

    }

    public void setDrinkDto(int id, UserSession userSession) {
        final var drink = drinkArrayList.stream()
                .filter(drink1 -> drink1.getId() == id)
                .findFirst()
                .orElseThrow();
        final var ordering = userSession.getCafeOrderDto();
        if (ordering.getDrink() == null) {
            ordering.setDrink(drink);
            int price = ordering.getPrice();
            ordering.setPrice(price + drink.getPrice());
        } else {
            int price = ordering.getPrice();
            final var beforeDrinkDto = ordering.getDrink();
            if (beforeDrinkDto.equals(drink)) {
                ordering.setPrice(price - ordering.getDrink().getPrice());
                ordering.setDrink(null);
            } else {
                ordering.setPrice(price - beforeDrinkDto.getPrice() + drink.getPrice());
                ordering.setDrink(drink);
            }
        }
    }

    public void setMilkDto(int id, UserSession userSession) {
        final var ordering = userSession.getCafeOrderDto();
        final var milk = milkArrayList.stream()
                .filter(milk1 -> milk1.getId() == id)
                .findFirst().orElse(new MilkDto());
        if (ordering.getMilk() == null) {
            ordering.setMilk(milk);
            int price = ordering.getPrice();
            ordering.setPrice(price + milk.getPrice());
        } else {
            final var beforeMilkDto = ordering.getMilk();
            int price = ordering.getPrice();
            if (beforeMilkDto.equals(milk)) {
                ordering.setPrice(price - ordering.getMilk().getPrice());
                ordering.setMilk(null);
            } else {
                ordering.setPrice(price - beforeMilkDto.getPrice() + milk.getPrice());
                ordering.setMilk(milk);
            }
        }
    }

    public void setSyrupDto(int id, UserSession userSession) {
        final var ordering = userSession.getCafeOrderDto();
        final var syrup = syrupLocalList.stream().filter(syrup1 -> syrup1.getId() == id).findFirst().orElse(new SyrupDto());
        if (ordering.getSyrup() == null) {
            ordering.setSyrup(syrup);
            int price = ordering.getPrice();
            ordering.setPrice(price + syrup.getPrice());
        } else {
            final var beforeSyrupDto = ordering.getSyrup();
            int price = ordering.getPrice();
            if (beforeSyrupDto.equals(syrup)) {
                ordering.setPrice(price - ordering.getSyrup().getPrice());
                ordering.setSyrup(null);
            } else {
                ordering.setPrice(price - beforeSyrupDto.getPrice() + syrup.getPrice());
                ordering.setSyrup(syrup);
            }
        }
    }

    public void setDessertDto(int id, UserSession userSession) {
        final var ordering = userSession.getCafeOrderDto();
        final var dessert = dessertLocalList.stream().filter(dessert1 -> dessert1.getId() == id).findFirst().orElse(new DessertDto());
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
        final var cafeOrderDto = userSession.getCafeOrderDto();
        cafeOrderDto.setComment(comment);
        final var orderRequest = orderingToOrderingDtoMapper.toOrderingDTO(cafeOrderDto);
        final var order = getApi.newOrder(orderRequest);
        final var cafeOrderClone = cafeOrderClonnable.clone(cafeOrderDto);
        model.addAttribute("orderID", order);
        model.addAttribute("cafeOrder", cafeOrderClone);
        model.addAttribute("mainPageLink", ClientEndpoint.MAIN_PAGE);
        CafeOrderUtil.clear(cafeOrderDto);
    }

    private void updateData() {
        try {
            if (syrupLocalList.isEmpty()) {
                final var syrups = getApi.getSyrupList();
                syrupLocalList.addAll(syrups);
            }
            if (dessertLocalList.isEmpty()) {
                final var desserts = getApi.getDessertList();
                dessertLocalList.addAll(desserts);
            }
            if (drinkArrayList.isEmpty()) {
                final var drinks = getApi.getDrinkList();
                drinkArrayList.addAll(drinks);
            }
            if (milkArrayList.isEmpty()) {
                final var milks = getApi.getMilkList();
                milkArrayList.addAll(milks);
            }
            if (coffeeDrinkDtoArrayList.isEmpty()) {
                coffeeDrinkDtoArrayList.addAll(drinkArrayList.stream()
                        .filter(DrinkDto::getIsCoffee)
                        .toList());
            }
            if (otherDrinkDtoArrayList.isEmpty()) {
                otherDrinkDtoArrayList.addAll(drinkArrayList);
                otherDrinkDtoArrayList.removeAll(coffeeDrinkDtoArrayList);
            }
            if (coffeeName.isEmpty()) {
                coffeeDrinkDtoArrayList.forEach(drink -> coffeeName.add(drink.getName()));
            }
            if (otherDrinkDtoName.isEmpty()) {
                otherDrinkDtoArrayList.forEach(drink -> otherDrinkDtoName.add(drink.getName()));
            }
            if (coffeeDrinkDtoHashMap.isEmpty()) {
                extractFromSet(coffeeName, coffeeDrinkDtoArrayList, coffeeDrinkDtoHashMap);
            }
            if (otherDrinkDtoHashMap.isEmpty()) {
                extractFromSet(otherDrinkDtoName, otherDrinkDtoArrayList, otherDrinkDtoHashMap);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void extractFromSet(Set<String> drink,
                                List<DrinkDto> drinkList,
                                HashMap<String, TreeSet<DrinkDto>> drinkHashMap) {
        drink.forEach(drinkHead -> {
            final var coffeeSet = new TreeSet<>(Comparator.comparingInt(DrinkDto::getId));
            coffeeSet.addAll(drinkList.stream()
                    .filter(d -> d.getName().equals(drinkHead))
                    .toList());
            drinkHashMap.put(drinkHead, coffeeSet);
        });
    }

    public void getBarista(Model model) {
        if (orderingArrayList.isEmpty()) {
            loadData();
            return;
        }
        final var newOrderCreate = getApi.updateBarista().get(0);
        if (BooleanUtils.toBoolean(newOrderCreate.getUpdate())) {
            loadData();
            getApi.makeUpdateFalse();
        }

        orderingArrayList.sort(Comparator.comparingInt(InOutOrderingDTO::getId));
        orderingTrueArrayList.sort(Comparator.comparingInt(InOutOrderingDTO::getId));
        Collections.reverse(orderingTrueArrayList);
        orderingFalseArrayList.sort(Comparator.comparingInt(InOutOrderingDTO::getId));
        Collections.reverse(orderingFalseArrayList);

        model.addAttribute("orderList", orderingArrayList);
        model.addAttribute("orderTrueList", orderingTrueArrayList);
        model.addAttribute("orderFalseList", orderingFalseArrayList);
        model.addAttribute("closeOrderLink", ClientEndpoint.CLOSE_ORDER);
    }

    public void closeOrder(int id) {
        final var edited = new OrderingDTO();
        edited.setOrderId(id);
        getApi.editOrder(edited);
    }

    private void loadData() {
        orderingArrayList.clear();
        orderingTrueArrayList.clear();
        orderingFalseArrayList.clear();
        final var todayOrders = getApi.getTodayOrderList();
        orderingArrayList.addAll(todayOrders);
        for (InOutOrderingDTO orderingDTO : orderingArrayList) {
            if (orderingDTO.getStatus()) {
                orderingTrueArrayList.add(orderingDTO);
            } else {
                orderingFalseArrayList.add(orderingDTO);
            }
        }
    }

}
