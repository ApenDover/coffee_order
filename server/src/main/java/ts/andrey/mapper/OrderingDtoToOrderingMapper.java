package ts.andrey.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ts.andrey.common.dto.OrderingDTO;
import ts.andrey.common.data.entity.Dessert;
import ts.andrey.common.data.entity.Ordering;
import ts.andrey.service.DessertService;
import ts.andrey.service.DrinkService;
import ts.andrey.service.MilkService;
import ts.andrey.service.SyrupService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {DessertService.class, MilkService.class, SyrupService.class, DrinkService.class})
public interface OrderingDtoToOrderingMapper {

    @Mapping(target = "id", source = "orderId")
    @Mapping(target = "milk", source = "milkId")
    @Mapping(target = "drink", source = "drinkId")
    @Mapping(target = "syrup", source = "syrupId")
    @Mapping(target = "desserts", source = "dess", qualifiedByName = "IntegerArrayToListDessert")
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "dateReady", ignore = true)
    Ordering toOrdering(OrderingDTO orderingDTO, @Context DessertService dessertService, @Context MilkService milkService,
                        @Context SyrupService syrupService, @Context DrinkService drinkService);

    @Named("IntegerArrayToListDessert")
    default List<Dessert> toIntegerArray(Integer[] desserts, @Context DessertService dessertService) {
        if (desserts == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(desserts).map(dessertService::findOne).collect(Collectors.toList());
    }


    @AfterMapping
    default void countPrice(@MappingTarget Ordering ordering) {
        int price = 0;

        if (ordering.getDrink() != null) {
            price += ordering.getDrink().getPrice();
        }
        if (ordering.getMilk() != null) {
            price += ordering.getMilk().getPrice();
        }
        if (ordering.getSyrup() != null) {
            price += ordering.getSyrup().getPrice();
        }
        if (ordering.getDesserts() != null) {
            for (Dessert dessert : ordering.getDesserts()) {
                price += dessert.getPrice();
            }
            ordering.getDesserts().forEach(dessert -> {
                dessert.setOrders(new ArrayList<>(Collections.singletonList(ordering)));
            });
        }
        ordering.setPrice(price);
        ordering.setDate(new Date());
    }

}
