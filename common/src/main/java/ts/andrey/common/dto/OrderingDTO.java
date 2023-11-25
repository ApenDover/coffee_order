package ts.andrey.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderingDTO {

    private int orderId;

    private int milkId;

    private int drinkId;

    private int syrupId;

    private List<Integer> dessertsId;

    private boolean status;

    private String comment;

}
