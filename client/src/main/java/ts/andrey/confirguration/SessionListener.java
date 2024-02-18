package ts.andrey.confirguration;


import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import ts.andrey.common.data.entity.Ordering;
import ts.andrey.rest.UserSession;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        final var userSession = new UserSession(new Ordering());
        event.getSession().setAttribute("userSession", userSession);
    }

}

