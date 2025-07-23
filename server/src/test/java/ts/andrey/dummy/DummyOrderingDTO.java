package ts.andrey.dummy;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ts.andrey.server.model.OrderingDTO;
import ts.andrey.utils.TestReflectionUtils;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class DummyOrderingDTO {

    public OrderingDTO getDefault() {
        final var dto = new OrderingDTO();
        dto.setSyrupId(1);
        dto.setDrinkId(2);
        dto.setMilkId(3);
        dto.setDessertsId(List.of(1));
        dto.setStatus(true);
        dto.setComment("comment");
        TestReflectionUtils.assertAllFieldsNotNullWithExclude(dto, "orderId");
        return dto;
    }

}
