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


//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@Mapper(componentModel = "spring")
//public abstract class OrderingDtoToOrderingMapper {
//
//    private final DessertService dessertService;
//    private final DrinkService drinkService;
//    private final MilkService milkService;
//    private final SyrupService syrupService;
//
//    @Mapping(target = "status", source = "status", defaultValue = "false")
//    @Mapping(target = "drink.id", source = "drinkId")
//    @Mapping(target = "milk.id", source = "milkId")
//    @Mapping(target = "syrup.id", source = "syrupId")
//    @Mapping(target = "desserts", source = "dessertsId")
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "price", ignore = true)
//    @Mapping(target = "date", ignore = true)
//    @Mapping(target = "dateReady", ignore = true)
//    public abstract CafeOrder toOrdering(OrderingDTO orderingDTO);
//
//    protected Dessert mapDessertId(Integer id) {
//        if (id == null) {
//            return null;
//        }
//        final var dessert = new Dessert();
//        dessert.setId(id);
//        return dessert;
//    }
//
//
//    @AfterMapping
//    protected void countPrice(@MappingTarget CafeOrder cafeOrder) {
//        int price = 0;
//
//        if (cafeOrder.getDrink() != null) {
//            price += cafeOrder.getDrink().getPrice();
//        }
//        if (cafeOrder.getMilk() != null) {
//            price += cafeOrder.getMilk().getPrice();
//        }
//        if (cafeOrder.getSyrup() != null) {
//            price += cafeOrder.getSyrup().getPrice();
//        }
//        if (cafeOrder.getDesserts() != null) {
//            for (Dessert dessert : cafeOrder.getDesserts()) {
//                price += dessert.getPrice();
//            }
//            cafeOrder.getDesserts().forEach(dessert ->
//                    dessert.setCafeOrders(new ArrayList<>(Collections.singletonList(cafeOrder))));
//        }
//        cafeOrder.setPrice(price);
//        cafeOrder.setDate(OffsetDateTime.now());
//    }
//
//}
