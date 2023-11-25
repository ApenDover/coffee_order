package ts.andrey.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ts.andrey.common.data.entity.Dessert;
import ts.andrey.common.data.entity.Ordering;
import ts.andrey.common.dto.OrderingDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderingToOrderingDtoMapper {

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "milk.id", target = "milkId")
    @Mapping(source = "drink.id", target = "drinkId")
    @Mapping(source = "syrup.id", target = "syrupId")
    @Mapping(source = "desserts", target = "dessertsId", qualifiedByName = "ToIntegerArray")
    OrderingDTO toOrderingDTO(Ordering ordering);

    @Named("ToIntegerArray")
    default List<Integer> toIntegerArray(List<Dessert> desserts) {
        if (desserts == null) {
            return Collections.EMPTY_LIST;
        }
        return desserts.stream()
                .map(Dessert::getId)
                .collect(Collectors.toList());
    }

}
