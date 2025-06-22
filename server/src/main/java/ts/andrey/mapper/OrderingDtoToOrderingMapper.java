package ts.andrey.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ts.andrey.entity.CafeOrder;
import ts.andrey.entity.Dessert;
import ts.andrey.server.model.OrderingDTO;
import ts.andrey.service.DessertService;
import ts.andrey.service.DrinkService;
import ts.andrey.service.MilkService;
import ts.andrey.service.SyrupService;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;

@Mapper(componentModel = "spring", uses = {DessertService.class, MilkService.class, SyrupService.class, DrinkService.class})
public interface OrderingDtoToOrderingMapper {

    @Mapping(target = "id", source = "orderId")
    @Mapping(target = "milk", source = "milkId")
    @Mapping(target = "drink", source = "drinkId")
    @Mapping(target = "syrup", source = "syrupId")
    @Mapping(target = "desserts", source = "dessertsId")
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "dateReady", ignore = true)
    @Mapping(target = "status", defaultValue = "false")
    CafeOrder toOrdering(OrderingDTO orderingDTO, @Context DessertService dessertService, @Context MilkService milkService,
                         @Context SyrupService syrupService, @Context DrinkService drinkService);

    default Dessert mapDessertId(Integer id, @Context DessertService dessertService) {
        if (id == null) {
            return null;
        }
        return dessertService.findOne(id);
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
        cafeOrder.setDate(OffsetDateTime.now());
    }

}
