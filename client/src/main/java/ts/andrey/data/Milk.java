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
@Table(name = "milk")
public class Milk implements Serializable {

    public static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer price;

    private List<CafeOrder> cafeOrder;

}
