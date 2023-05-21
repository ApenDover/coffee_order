package ts.andrey.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import ts.andrey.common.data.entity.Dessert;
import ts.andrey.common.data.entity.Ordering;
import ts.andrey.common.dto.OrderingDTO;


import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderingToOrderingDtoMapper {

    OrderingToOrderingDtoMapper INSTANCE = Mappers.getMapper(OrderingToOrderingDtoMapper.class);

    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "milk.id", target = "milkId")
    @Mapping(source = "drink.id", target = "drinkId")
    @Mapping(source = "syrup.id", target = "syrupId")
    @Mapping(source = "desserts", target = "dess", qualifiedByName = "ToIntegerArray")
    OrderingDTO toOrderingDTO(Ordering ordering);

    @Named("ToIntegerArray")
    default Integer[] toIntegerArray(List<Dessert> desserts) {
        if (desserts == null) {
            return null;
        }
        Integer[] arr = new Integer[desserts.size()];
        for (int i = 0; i < desserts.size(); i++) {
            arr[i] = desserts.get(i).getId();
        }
        return arr;
    }

}
