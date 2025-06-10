package ts.andrey.confirguration.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ts.andrey.data.User;
import ts.andrey.service.GetApi;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserInfoUserDetailsService implements UserDetailsService {

    private final UserInfoUserDetails userInfoUserDetails;

    private final GetApi getApi;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            final var password = getApi.getPassword(username);
            userInfoUserDetails.setUser(User.builder()
                    .name(username)
                    .password(addSoltToPassword(password))
                    .build());
            return userInfoUserDetails;
        } catch (Exception e) {
            log.error("getPassword api error", e);
            throw new UsernameNotFoundException("user not found " + username);
        }
    }

    private String addSoltToPassword(String password) {
        return "{SHA-256}{wcUyP2F04JSIY9h+sM1ImZ+KDkxgc5y2dJsS8F/Qg0c=}" + password;
    }

}
