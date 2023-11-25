package ts.andrey.common.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "ordering")
public class Ordering implements Cloneable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderingSequence")
    @SequenceGenerator(name = "orderingSequence", sequenceName = "seq_ordering_id", allocationSize = 1)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "drink_id", referencedColumnName = "id")
    private Drink drink;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "milk_id", referencedColumnName = "id")
    private Milk milk;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "syrup_id", referencedColumnName = "id")
    private Syrup syrup;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "dessert_order",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "dessert_id", referencedColumnName = "id"))
    private List<Dessert> desserts;

    private int price;

    private boolean status;

    @Column(name = "date_create")
    private LocalDateTime date;

    @Column(name = "date_ready")
    private LocalDateTime dateReady;

    private String comment;

    @Override
    public Ordering clone() throws CloneNotSupportedException {
        return (Ordering) super.clone();
    }

    public void clear() {
        this.id = 0;
        this.drink = new Drink();
        this.milk = new Milk();
        this.syrup = new Syrup();
        this.desserts = new ArrayList<>();
        this.price = 0;
        this.status = false;
        this.date = LocalDateTime.now();
        this.dateReady = LocalDateTime.now();
        this.comment = "";
    }

}
