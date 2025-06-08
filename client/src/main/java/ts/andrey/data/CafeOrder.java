package ts.andrey.data;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class CafeOrder implements Serializable, Cloneable {

    public static final long serialVersionUID = 1L;

    private Integer id;

    private Drink drink;

    private Milk milk;

    private Syrup syrup;

    private List<Dessert> desserts;

    private Integer price;

    private Boolean status;

    @Column(name = "date_create")
    private LocalDateTime date;

    @Column(name = "date_ready")
    private LocalDateTime dateReady;

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

    public boolean isEmpty() {
        return Objects.isNull(this.drink) && Objects.isNull(this.desserts);
    }

    public CafeOrder clone() throws CloneNotSupportedException {
        return (CafeOrder) super.clone();
    }

}
