package ts.andrey.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.proxy.HibernateProxy;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
@Entity
public class CafeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderingSequence")
    @SequenceGenerator(name = "orderingSequence", sequenceName = "seq_order_id", allocationSize = 5)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "drink_id", referencedColumnName = "id")
    @ToString.Exclude
    private Drink drink;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "milk_id", referencedColumnName = "id")
    @ToString.Exclude
    private Milk milk;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "syrup_id", referencedColumnName = "id")
    @ToString.Exclude
    private Syrup syrup;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "dessert_order",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "dessert_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Dessert> desserts;

    private Integer price;

    private Boolean status;

    @Column(name = "date_create")
    private OffsetDateTime date;

    @Column(name = "date_ready")
    private OffsetDateTime dateReady;

    private String comment;

    @SuppressWarnings("PMD.NullAssignment")
    public void clear() {
        this.id = 0;
        this.drink = null;
        this.milk = null;
        this.syrup = null;
        this.desserts = null;
        this.price = 0;
        this.status = false;
        this.date = null;
        this.dateReady = null;
        this.comment = "";
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        Class<?> oEffectiveClass = object instanceof HibernateProxy
                ? ((HibernateProxy) object).getHibernateLazyInitializer().getPersistentClass()
                : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) {
            return false;
        }
        final var that = (CafeOrder) object;
        return this.id != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

}
