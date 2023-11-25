package ts.andrey.common.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "syrup")
public class Syrup {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "syrupSequence")
    @SequenceGenerator(name = "syrupSequence", sequenceName = "seq_syrup_id", allocationSize = 1)
    private int id;

    private String name;

    private int price;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "syrup")
    private List<Ordering> ordering;

}
