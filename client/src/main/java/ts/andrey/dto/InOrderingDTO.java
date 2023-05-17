package ts.andrey.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
public class InOrderingDTO implements Comparable<InOrderingDTO> {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    public String getDessert() {
        if (dessert.isEmpty()) {
            return null;
        }
        final var stringBuilder = new StringBuilder();
        stringBuilder.append(dessert.get(0));
        for (int i = 1; i < dessert.size(); i++) {
            stringBuilder.append(", ");
            stringBuilder.append(dessert.get(i));
        }
        return stringBuilder.toString();
    }

    public String getDateOrder() {
        if (dateOrder == null) {
            return null;
        }
        return FORMATTER.format(dateOrder);
    }

    public String getDateReady() {
        if (dateReady == null) {
            return null;
        }
        return FORMATTER.format(dateReady);
    }

    @Override
    public int compareTo(InOrderingDTO o) {
        return Integer.compare(this.id, o.getId());
    }

}
