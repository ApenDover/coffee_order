package ts.andrey.data;


import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
@Table(name = "dessert")
public class Dessert {

    private Integer id;

    private String name;

    private Integer price;

    private List<CafeOrder> cafeOrders;

}
