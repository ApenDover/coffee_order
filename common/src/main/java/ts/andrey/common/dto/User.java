package ts.andrey.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class User implements Serializable {

    public static final long serialVersionUID = 1L;

    private String name;

    private String password;

}
