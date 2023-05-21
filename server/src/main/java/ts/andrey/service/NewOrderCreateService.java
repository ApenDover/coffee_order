package ts.andrey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ts.andrey.common.data.entity.NewOrderCreate;
import ts.andrey.repositories.NewOrderCreateRepository;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class NewOrderCreateService {

    private final NewOrderCreateRepository newOrderCreateRepository;

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
        newOrderCreateRepository.save(giveIt().setUpdate(true).setUpdateTime(new Date()));
    }

    @Transactional
    public void makeFalse() {
        checkIt();
        newOrderCreateRepository.save(giveIt().setUpdate(false).setUpdateTime(new Date()));
    }

    private void checkIt() {
        if (giveIt() == null) {
            newOrderCreateRepository.save(new NewOrderCreate().setId(1).setUpdateTime(new Date()).setUpdate(false));
        }
    }

}
