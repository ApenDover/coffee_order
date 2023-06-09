package ts.andrey.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.common.data.entity.Dessert;
import ts.andrey.repositories.DessertRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DessertService {

    private final DessertRepository dessertRepository;

    public List<Dessert> findAll() {
        return dessertRepository.findAll();
    }

    public Dessert findOne(int id) {
        return dessertRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Dessert dessert) {
        dessertRepository.save(dessert);
    }

}
