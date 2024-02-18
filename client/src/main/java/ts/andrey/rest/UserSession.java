package ts.andrey.rest;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ts.andrey.common.data.entity.Ordering;

@RequiredArgsConstructor
@Data
public class UserSession {

    private final Ordering order;

}
