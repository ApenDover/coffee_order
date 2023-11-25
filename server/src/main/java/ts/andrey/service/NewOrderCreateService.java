package ts.andrey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.common.data.entity.NewOrderCreate;
import ts.andrey.repositories.NewOrderCreateRepository;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class NewOrderCreateService {

    private final NewOrderCreateRepository newOrderCreateRepository;



    @PostConstruct
    private NewOrderCreate giveIt() {
        return newOrderCreateRepository.findById(1).orElse(null);
    }

    public NewOrderCreate getNewOrderCreate() {
        checkIt();
        return giveIt();
    }

    @Transactional
    public void makeTrue() {
        checkIt();
        newOrderCreateRepository.save(giveIt().setUpdate(true).setUpdateTime(LocalDateTime.now()));
    }

    @Transactional
    public void makeFalse() {
        checkIt();
        newOrderCreateRepository.save(giveIt().setUpdate(false).setUpdateTime(LocalDateTime.now()));
    }

    private void checkIt() {
        if (Objects.isNull(giveIt())) {
            newOrderCreateRepository.save(new NewOrderCreate().setId(1).setUpdateTime(LocalDateTime.now()).setUpdate(false));
        }
    }

}
