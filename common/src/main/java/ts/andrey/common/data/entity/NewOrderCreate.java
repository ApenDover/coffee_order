package ts.andrey.common.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
public class NewOrderCreate {

    @Id
    private int id;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    private boolean update;

}
