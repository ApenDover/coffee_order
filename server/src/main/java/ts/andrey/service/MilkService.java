package ts.andrey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.entity.Milk;
import ts.andrey.exception.CoffeeServerException;
import ts.andrey.repositories.MilkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MilkService {

    private static final String MILK_NAME = "Молоко";
    private final MilkRepository milkRepository;

    public List<Milk> findAll() {
        return milkRepository.findAll();
    }

    public Milk findOne(int id) {
        return milkRepository.findById(id)
                .orElseThrow(() -> new CoffeeServerException(MILK_NAME, id));
    }

    @Transactional
    public void save(Milk milk) {
        milkRepository.save(milk);
    }

}
