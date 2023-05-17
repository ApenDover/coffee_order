package ts.andrey.models;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Ordering implements Cloneable {

    private int id;
    private int price;
    private Drink drink;
    private Milk milk;
    private Syrup syrup;
    private List<Dessert> desserts;
    private Boolean status;
    private Date date;
    private Date dateReady;
    private String comment;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void clear() {
        this.comment = null;
        this.id = 0;
        this.price = 0;
        this.drink = null;
        this.milk = null;
        this.syrup = null;
        this.desserts = null;
        this.status = null;
        this.date = null;
        this.dateReady = null;
    }

}
