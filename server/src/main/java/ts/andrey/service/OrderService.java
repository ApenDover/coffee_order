package ts.andrey.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.common.data.entity.Ordering;
import ts.andrey.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Ordering> findAll() {
        return orderRepository.findAll();
    }

    public List<Ordering> findToday() {
        return orderRepository.findAllByDateAfter();
    }

    public void update(boolean status, LocalDateTime date, int id) {
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
