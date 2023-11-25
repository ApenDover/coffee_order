package ts.andrey.common.data.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Entity
public class NewOrderCreate {

    @Id
    private int id;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    private boolean update;

}
