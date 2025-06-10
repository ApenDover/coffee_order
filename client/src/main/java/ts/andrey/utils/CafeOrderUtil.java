package ts.andrey.utils;

import lombok.experimental.UtilityClass;
import ts.andrey.model.CafeOrderDto;

import java.util.Objects;

@UtilityClass
public class CafeOrderUtil {

    @SuppressWarnings("PMD.NullAssignment")
    public void clear(CafeOrderDto cafeOrder) {
        cafeOrder.setId(0);
        cafeOrder.setDrink(null);
        cafeOrder.setMilk(null);
        cafeOrder.setSyrup(null);
        cafeOrder.setDesserts(null);
        cafeOrder.setPrice(0);
        cafeOrder.setStatus(false);
        cafeOrder.setDate(null);
        cafeOrder.setDateReady(null);
        cafeOrder.setComment("");
    }

    public boolean isEmpty(CafeOrderDto cafeOrder) {
        return Objects.isNull(cafeOrder.getDrink()) && Objects.isNull(cafeOrder.getDesserts());
    }

}
