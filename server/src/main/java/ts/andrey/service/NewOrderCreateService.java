package ts.andrey.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.entity.NewOrderCreate;
import ts.andrey.mapper.DtoEntityMapper;
import ts.andrey.repositories.NewOrderCreateRepository;
import ts.andrey.server.model.NewOrderCreateDto;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class NewOrderCreateService {

    private final NewOrderCreateRepository newOrderCreateRepository;
    private final DtoEntityMapper dtoEntityMapper;

    @PostConstruct
    private NewOrderCreate getNewOrderCreateOrCreateThis() {
        final var newOrderOpt = newOrderCreateRepository.findById(1);
        if (newOrderOpt.isPresent()) {
            return newOrderOpt.get();
        }
        final var newOrder = new NewOrderCreate()
                .setId(1)
                .setUpdateTime(OffsetDateTime.now())
                .setUpdate(false);
        return newOrderCreateRepository.save(newOrder);
    }

    public NewOrderCreateDto getNewOrderCreate() {
        final var newOrder = getNewOrderCreateOrCreateThis();
        return dtoEntityMapper.mapCafeOrder(newOrder);
    }

    @Transactional
    public void makeTrue() {
        final var createdOrder = getNewOrderCreateOrCreateThis().setUpdate(true)
                .setUpdateTime(OffsetDateTime.now());
        newOrderCreateRepository.save(createdOrder);
    }

    @Transactional
    public void makeFalse() {
        final var newOrderCreate = getNewOrderCreateOrCreateThis()
                .setUpdate(false)
                .setUpdateTime(OffsetDateTime.now());
        newOrderCreateRepository.save(newOrderCreate);
    }

}
