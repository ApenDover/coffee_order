package ts.andrey.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ts.andrey.model.CafeOrderDto;
import ts.andrey.model.DessertDto;
import ts.andrey.model.OrderingDTO;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderingToOrderingDtoMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "milk.id", target = "milkId")
    @Mapping(source = "drink.id", target = "drinkId")
    @Mapping(source = "syrup.id", target = "syrupId")
    @Mapping(source = "desserts", target = "dessertsId", qualifiedByName = "ToIntegerArray")
    OrderingDTO toOrderingDTO(CafeOrderDto cafeOrder);

    @Named("ToIntegerArray")
    default List<Integer> toIntegerArray(List<DessertDto> desserts) {
        if (desserts == null) {
            return Collections.emptyList();
        }
        return desserts.stream()
                .map(DessertDto::getId).toList();
    }

}
