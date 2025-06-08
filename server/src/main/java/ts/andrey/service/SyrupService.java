package ts.andrey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.entity.Syrup;
import ts.andrey.exception.CoffeeServerException;
import ts.andrey.repositories.SyrupRepository;

import java.util.List;

@Service
@Transactional
public class SyrupService {

    private static final String SYRUP_NAME = "Сироп";
    private final transient SyrupRepository syrupRepository;

    @Autowired
    public SyrupService(SyrupRepository syrupRepository) {
        this.syrupRepository = syrupRepository;
    }

    public List<Syrup> findAll() {
        return syrupRepository.findAll();
    }

    public Syrup findOne(int id) {
        return syrupRepository.findById(id)
                .orElseThrow(() -> new CoffeeServerException(SYRUP_NAME, id));
    }

    @Transactional
    public void save(Syrup syrup) {
        syrupRepository.save(syrup);
    }

}
