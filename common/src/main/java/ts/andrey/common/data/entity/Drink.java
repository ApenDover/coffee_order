package ts.andrey.common.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "drink")
public class Drink implements Comparable<Drink> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drinkSequence")
    @SequenceGenerator(name = "drinkSequence", sequenceName = "seq_drink_id", allocationSize = 1)
    private int id;

    private String name;

    private int size;

    private int price;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "drink")
    private List<Ordering> ordering;

    private boolean coffee;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Drink)) {
            return false;
        }
        Drink drink = (Drink) o;
        return getId() == drink.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public int compareTo(Drink o) {
        return Integer.compare(this.id, o.getId());
    }

}
