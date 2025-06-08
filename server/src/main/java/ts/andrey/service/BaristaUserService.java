package ts.andrey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.entity.BaristaUser;
import ts.andrey.exception.CoffeeServerException;
import ts.andrey.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class BaristaUserService {

    private static final String BARISTA_NAME = "Бариста";
    private final UserRepository userRepository;

    public BaristaUser findByName(String user) {
        final var userList = userRepository.findBaristaUserByName(user);
        return userList.stream()
                .findFirst()
                .orElseThrow(() -> new CoffeeServerException(BARISTA_NAME, user));
    }

}
