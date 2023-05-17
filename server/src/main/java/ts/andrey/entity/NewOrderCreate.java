package ts.andrey.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
public class NewOrderCreate {

    @Id
    private int id;

    @Column(name = "update_time")
    private Date updateTime;

    private boolean update;

}
