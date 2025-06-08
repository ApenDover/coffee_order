package ts.andrey.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.entity.Drink;
import ts.andrey.exception.CoffeeServerException;
import ts.andrey.repositories.DrinkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DrinkService {

    private static final String DRINK_NAME = "Напиток";
    private final DrinkRepository drinkRepository;

    public List<Drink> findAll() {
        return drinkRepository.findAll();
    }

    public Drink findOne(int id) {
        return drinkRepository.findById(id)
                .orElseThrow(() -> new CoffeeServerException(DRINK_NAME, id));
    }

    @Transactional
    public void save(Drink coffee) {
        drinkRepository.save(coffee);
    }

}
