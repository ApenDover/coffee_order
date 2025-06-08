package ts.andrey.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ts.andrey.entity.CafeOrder;
import ts.andrey.exception.CoffeeServerException;
import ts.andrey.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    public static final String ORDER_NAME = "Заказ";
    private final OrderRepository orderRepository;

    public List<CafeOrder> findAll() {
        return orderRepository.findAll();
    }

    public List<CafeOrder> findToday() {
        return orderRepository.findAllByDateAfter();
    }

    public void update(boolean status, LocalDateTime date, int id) {
        orderRepository.update(status, date, id);
    }

    public CafeOrder findOne(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new CoffeeServerException(ORDER_NAME, id));
    }

    @Transactional
    public CafeOrder save(CafeOrder cafeOrder) {
        return orderRepository.save(cafeOrder);
    }

}
