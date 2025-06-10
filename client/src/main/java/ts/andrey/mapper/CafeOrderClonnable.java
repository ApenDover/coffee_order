package ts.andrey.mapper;

import org.mapstruct.Mapper;
import ts.andrey.model.CafeOrderDto;

@Mapper(componentModel = "spring")
public interface CafeOrderClonnable {

    CafeOrderDto clone(CafeOrderDto cafeOrder);

}
