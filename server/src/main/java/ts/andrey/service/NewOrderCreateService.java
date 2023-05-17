package ts.andrey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ts.andrey.entity.NewOrderCreate;
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
        NewOrderCreate newOrderCreate = giveIt();
        newOrderCreate.setUpdate(true);
        newOrderCreate.setUpdateTime(new Date());
        newOrderCreateRepository.save(newOrderCreate);
    }

    @Transactional
    public void makeFalse() {
        checkIt();
        NewOrderCreate newOrderCreate = giveIt();
        newOrderCreate.setUpdate(false);
        newOrderCreate.setUpdateTime(new Date());
        newOrderCreateRepository.save(newOrderCreate);
    }

    private void checkIt() {
        if (giveIt() == null) {
            NewOrderCreate newOrderCreate = new NewOrderCreate();
            newOrderCreate.setId(1);
            newOrderCreate.setUpdate(false);
            newOrderCreate.setUpdateTime(new Date());
            newOrderCreateRepository.save(newOrderCreate);
        }
    }

}
