package ts.andrey.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ts.andrey.common.dto.OrderingDTO;
import ts.andrey.entity.CafeOrder;
import ts.andrey.entity.Dessert;
import ts.andrey.service.DessertService;
import ts.andrey.service.DrinkService;
import ts.andrey.service.MilkService;
import ts.andrey.service.SyrupService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {DessertService.class, MilkService.class, SyrupService.class, DrinkService.class})
public interface OrderingDtoToOrderingMapper {

    @Mapping(target = "id", source = "orderId")
    @Mapping(target = "milk", source = "milkId")
    @Mapping(target = "drink", source = "drinkId")
    @Mapping(target = "syrup", source = "syrupId")
    @Mapping(target = "desserts", source = "dessertsId", qualifiedByName = "IntegerArrayToListDessert")
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "dateReady", ignore = true)
    @Mapping(target = "status", defaultValue = "false")
    CafeOrder toOrdering(OrderingDTO orderingDTO, @Context DessertService dessertService, @Context MilkService milkService,
                         @Context SyrupService syrupService, @Context DrinkService drinkService);

    @Named("IntegerArrayToListDessert")
    default List<Dessert> toIntegerArray(List<Integer> desserts, @Context DessertService dessertService) {
        if (desserts == null) {
            return Collections.emptyList();
        }
        return desserts.stream().map(dessertService::findOne).collect(Collectors.toList());
    }


    @AfterMapping
    default void countPrice(@MappingTarget CafeOrder cafeOrder) {
        int price = 0;

        if (cafeOrder.getDrink() != null) {
            price += cafeOrder.getDrink().getPrice();
        }
        if (cafeOrder.getMilk() != null) {
            price += cafeOrder.getMilk().getPrice();
        }
        if (cafeOrder.getSyrup() != null) {
            price += cafeOrder.getSyrup().getPrice();
        }
        if (cafeOrder.getDesserts() != null) {
            for (Dessert dessert : cafeOrder.getDesserts()) {
                price += dessert.getPrice();
            }
            cafeOrder.getDesserts().forEach(dessert ->
                    dessert.setCafeOrders(new ArrayList<>(Collections.singletonList(cafeOrder))));
        }
        cafeOrder.setPrice(price);
        cafeOrder.setDate(LocalDateTime.now());
    }

}
