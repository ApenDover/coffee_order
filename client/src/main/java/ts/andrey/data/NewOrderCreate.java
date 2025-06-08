package ts.andrey.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Accessors(chain = true)
public class NewOrderCreate implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    private boolean update;

}
