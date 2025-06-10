package ts.andrey.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ts.andrey.entity.CafeOrder;
import ts.andrey.entity.Dessert;
import ts.andrey.entity.Drink;
import ts.andrey.entity.Milk;
import ts.andrey.entity.NewOrderCreate;
import ts.andrey.entity.Syrup;
import ts.andrey.server.model.CafeOrderDto;
import ts.andrey.server.model.DessertDto;
import ts.andrey.server.model.DrinkDto;
import ts.andrey.server.model.MilkDto;
import ts.andrey.server.model.NewOrderCreateDto;
import ts.andrey.server.model.SyrupDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DtoEntityMapper {

    NewOrderCreateDto mapCafeOrder(NewOrderCreate newOrderCreate);

    @Mapping(target = "drink", expression = "java(mapDrink(newOrderCreate.getDrink()))")
    @Mapping(target = "milk", expression = "java(mapMilk(newOrderCreate.getMilk()))")
    @Mapping(target = "syrup", expression = "java(mapSyrup(newOrderCreate.getSyrup()))")
    @Mapping(target = "desserts", expression = "java(mapDessert(newOrderCreate.getDesserts()))")
    CafeOrderDto mapCafeOrder(CafeOrder newOrderCreate);

    MilkDto mapMilk(Milk milk);

    List<MilkDto> mapMilkDto(List<Milk> milks);

    SyrupDto mapSyrup(Syrup milk);

    List<SyrupDto> mapSyrup(List<Syrup> milk);

    DessertDto mapDessert(Dessert dessert);

    List<DessertDto> mapDessert(List<Dessert> desserts);

    DrinkDto mapDrink(Drink drink);

    List<DrinkDto> mapDrinkDto(List<Drink> drink);

    Milk mapMilkDto(MilkDto milkDto);

    List<Milk> mapMilk(List<MilkDto> milkDto);

    Drink mapDrinkDto(DrinkDto drinkDto);

    Syrup mapSyrupDto(SyrupDto syrupDto);

    Dessert mapDessertDto(DessertDto syrupDto);

    List<Dessert> mapDessertDto(List<DessertDto> syrupDto);

}
