package ts.andrey.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
public class OutOrderingDTO {

    private int id;
    private String drink;
    private String milk;
    private String syrup;
    private List<String> dessert;
    private int price;
    private boolean status;
    private Date dateOrder;
    private Date dateReady;
    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutOrderingDTO)) {
            return false;
        }
        OutOrderingDTO that = (OutOrderingDTO) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
