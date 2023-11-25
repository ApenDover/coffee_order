package ts.andrey.common.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "milk")
public class Milk {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "milkSequence")
    @SequenceGenerator(name = "milkSequence", sequenceName = "seq_milk_id", allocationSize = 1)
    private int id;

    private String name;

    private int price;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "milk")
    private List<Ordering> ordering;

}
