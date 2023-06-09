package ts.andrey.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.common.data.entity.Drink;
import ts.andrey.repositories.DrinkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DrinkService {

    private final DrinkRepository drinkRepository;

    public List<Drink> findAll() {
        return drinkRepository.findAll();
    }

    public Drink findOne(int id) {
        return drinkRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Drink coffee) {
        drinkRepository.save(coffee);
    }

}
