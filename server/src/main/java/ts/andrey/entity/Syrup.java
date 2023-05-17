package ts.andrey.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
