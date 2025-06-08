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
@Table(name = "drink")
public class Drink implements Serializable, Comparable<Drink> {

    public static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Integer size;

    private Integer price;

    private List<CafeOrder> cafeOrder;

    private Boolean isCoffee;

    @Override
    public int compareTo(Drink o) {
        return Integer.compare(this.id, o.getId());
    }

}
