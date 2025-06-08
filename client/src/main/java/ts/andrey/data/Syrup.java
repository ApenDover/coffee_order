package ts.andrey.data;

import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
@Table(name = "syrup")
public class Syrup implements Serializable {

    public static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer price;

    private List<CafeOrder> cafeOrders;

}
