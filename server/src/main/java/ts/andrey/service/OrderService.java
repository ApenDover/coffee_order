package ts.andrey.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ts.andrey.entity.Ordering;
import ts.andrey.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Ordering> findAll() {
        return orderRepository.findAll();
    }

    public int getCount() {
        return orderRepository.getCount();
    }

    public void update(boolean status, Date date, int id) {
        orderRepository.update(status, date, id);
    }

    public Ordering findOne(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public Ordering save(Ordering order) {
        return orderRepository.save(order);
    }

}
