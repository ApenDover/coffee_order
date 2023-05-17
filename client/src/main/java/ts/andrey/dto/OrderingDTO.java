package ts.andrey.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderingDTO {

    private int orderId;
    private int milkId;
    private int drinkId;
    private int syrupId;
    private Integer[] dess;
    private boolean status;
    private String comment;

}
