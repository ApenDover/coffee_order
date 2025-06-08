package ts.andrey.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderingDTO {

    private Integer orderId;

    private Integer milkId;

    private Integer drinkId;

    private Integer syrupId;

    private List<Integer> dessertsId;

    private Boolean status;

    private String comment;

}
