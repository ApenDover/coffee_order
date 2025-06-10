package ts.andrey.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ts.andrey.entity.CafeOrder;
import ts.andrey.entity.Dessert;
import ts.andrey.server.model.InOutOrderingDTO;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderingToOutOrderingDtoMapper {

    @Mapping(source = "drink.name", target = "drink")
    @Mapping(source = "milk.name", target = "milk")
    @Mapping(source = "syrup.name", target = "syrup")
    @Mapping(source = "date", target = "dateOrder")
    @Mapping(source = "desserts", target = "dessert", qualifiedByName = "getDessertNames")
    InOutOrderingDTO mapToDto(CafeOrder cafeOrder);

    @Named("getDessertNames")
    default List<String> getDessertNames(List<Dessert> desserts) {
        List<String> dessertNames = new ArrayList<>();
        for (Dessert dessert : desserts) {
            dessertNames.add(dessert.getName());
        }
        return dessertNames;
    }

}
