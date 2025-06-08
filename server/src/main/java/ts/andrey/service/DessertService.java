package ts.andrey.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.entity.Dessert;
import ts.andrey.exception.CoffeeServerException;
import ts.andrey.repositories.DessertRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DessertService {

    private static final String DESSERT_NAME = "Десерт";
    private final DessertRepository dessertRepository;

    public List<Dessert> findAll() {
        return dessertRepository.findAll();
    }

    public Dessert findOne(int id) {
        return dessertRepository.findById(id)
                .orElseThrow(() -> new CoffeeServerException(DESSERT_NAME, id));
    }

    @Transactional
    public void save(Dessert dessert) {
        dessertRepository.save(dessert);
    }

}
