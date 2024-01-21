package ts.andrey.confirguration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ts.andrey.common.dto.User;
import ts.andrey.common.utility.FixUrl;
import ts.andrey.constants.CoffeeRestConst;
import ts.andrey.service.GetApi;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserInfoUserDetailsService implements UserDetailsService {

    private final UserInfoUserDetails userInfoUserDetails;

    private final GetApi getApi;

    private final CoffeeRestConst coffeeRestConst;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<String> password = getApi.getPassword(FixUrl.enterParamsIfNeed(coffeeRestConst
                .getPasswordForUserEndPoint(), username), username);
        if (password.isPresent()) {
            userInfoUserDetails.setUser(User.builder()
                    .name(username)
                    .password(addSoltToPassword(password.get()))
                    .build());
            return userInfoUserDetails;
        }
        throw new UsernameNotFoundException("user not found " + username);
    }

    private String addSoltToPassword(String password) {
        return "{SHA-256}{wcUyP2F04JSIY9h+sM1ImZ+KDkxgc5y2dJsS8F/Qg0c=}" + password;
    }

}
