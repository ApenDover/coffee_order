package ts.andrey.rest;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ts.andrey.data.CafeOrder;

import java.io.Serializable;

@RequiredArgsConstructor
@Data
public class UserSession implements Serializable {

    public static final long serialVersionUID = 1L;

    private final CafeOrder cafeOrder;

}
