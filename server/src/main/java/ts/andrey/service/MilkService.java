package ts.andrey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.common.data.entity.Milk;
import ts.andrey.repositories.MilkRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MilkService {

    private final MilkRepository milkRepository;

    public List<Milk> findAll() {
        return milkRepository.findAll();
    }

    public Milk findOne(int id) {
        return milkRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Milk milk) {
        milkRepository.save(milk);
    }

}
