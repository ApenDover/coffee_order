package ts.andrey.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class Drink implements Comparable<Drink> {

    private int id;

    private String name;

    private int size;

    private int price;

    @JsonIgnore
    @ToString.Exclude
    private List<Ordering> ordering;

    private boolean coffee;

    @Override
    public int compareTo(Drink o) {
        return Integer.compare(this.price, o.getPrice());
    }

}
