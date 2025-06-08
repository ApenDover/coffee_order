package ts.andrey.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.entity.NewOrderCreate;
import ts.andrey.repositories.NewOrderCreateRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class NewOrderCreateService {

    private final NewOrderCreateRepository newOrderCreateRepository;

    @PostConstruct
    private NewOrderCreate getNewOrderCreateOrCreateThis() {
        final var newOrderOpt = newOrderCreateRepository.findById(1);
        if (newOrderOpt.isPresent()) {
            return newOrderOpt.get();
        }
        final var newOrder = new NewOrderCreate()
                .setId(1)
                .setUpdateTime(LocalDateTime.now())
                .setUpdate(false);
        return newOrderCreateRepository.save(newOrder);
    }

    public NewOrderCreate getNewOrderCreate() {
        return getNewOrderCreateOrCreateThis();
    }

    @Transactional
    public void makeTrue() {
        final var createdOrder = getNewOrderCreateOrCreateThis().setUpdate(true)
                .setUpdateTime(LocalDateTime.now());
        newOrderCreateRepository.save(createdOrder);
    }

    @Transactional
    public void makeFalse() {
        final var newOrderCreate = getNewOrderCreateOrCreateThis()
                .setUpdate(false)
                .setUpdateTime(LocalDateTime.now());
        newOrderCreateRepository.save(newOrderCreate);
    }

}
