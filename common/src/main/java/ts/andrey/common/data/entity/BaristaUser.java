package ts.andrey.common.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class BaristaUser {

    @Id
    private int id;

    private String name;

    private String password;


}
