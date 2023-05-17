package ts.andrey.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class Syrup {

    private int id;

    private String name;

    private int price;

    @JsonIgnore
    @ToString.Exclude
    private List<Ordering> ordering;

}
