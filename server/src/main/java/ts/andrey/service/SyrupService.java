package ts.andrey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ts.andrey.common.data.entity.Syrup;
import ts.andrey.repositories.SyrupRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SyrupService {

    private final SyrupRepository syrupRepository;

    @Autowired
    public SyrupService(SyrupRepository syrupRepository) {
        this.syrupRepository = syrupRepository;
    }

    public List<Syrup> findAll() {
        return syrupRepository.findAll();
    }

    public Syrup findOne(int id) {
        return syrupRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Syrup syrup) {
        syrupRepository.save(syrup);
    }

}
