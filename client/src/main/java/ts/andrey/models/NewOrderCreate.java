package ts.andrey.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class NewOrderCreate {

    private int id;
    private Date updateTime;
    private boolean update;

}
