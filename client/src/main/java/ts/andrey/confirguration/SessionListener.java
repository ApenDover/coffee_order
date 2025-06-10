package ts.andrey.confirguration;


import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import ts.andrey.model.CafeOrderDto;
import ts.andrey.rest.UserSession;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        final var order = new CafeOrderDto();
        order.setPrice(0);
        final var userSession = new UserSession(order);
        event.getSession().setAttribute("userSession", userSession);
    }

}

