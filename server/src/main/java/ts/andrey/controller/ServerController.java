package ts.andrey.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ts.andrey.common.data.entity.Dessert;
import ts.andrey.common.data.entity.Drink;
import ts.andrey.common.data.entity.Milk;
import ts.andrey.common.data.entity.NewOrderCreate;
import ts.andrey.common.data.entity.Ordering;
import ts.andrey.common.data.entity.Syrup;
import ts.andrey.common.dto.InOutOrderingDTO;
import ts.andrey.common.dto.OrderingDTO;
import ts.andrey.mapper.OrderingDtoToOrderingMapper;
import ts.andrey.mapper.OrderingToOutOrderingDtoMapper;
import ts.andrey.service.BaristaUserService;
import ts.andrey.service.DessertService;
import ts.andrey.service.DrinkService;
import ts.andrey.service.MilkService;
import ts.andrey.service.NewOrderCreateService;
import ts.andrey.service.OrderService;
import ts.andrey.service.SyrupService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static ts.andrey.common.rest.ServerEndpoint.ALL_TODAY_ORDERS;
import static ts.andrey.common.rest.ServerEndpoint.API;
import static ts.andrey.common.rest.ServerEndpoint.CLOSE_ORDER;
import static ts.andrey.common.rest.ServerEndpoint.DESSERT_LIST;
import static ts.andrey.common.rest.ServerEndpoint.DRINK_LIST;
import static ts.andrey.common.rest.ServerEndpoint.GET_ORDER_ID;
import static ts.andrey.common.rest.ServerEndpoint.MAKE_UPDATE_FALSE;
import static ts.andrey.common.rest.ServerEndpoint.MILK_LIST;
import static ts.andrey.common.rest.ServerEndpoint.NEW_DESSERT;
import static ts.andrey.common.rest.ServerEndpoint.NEW_DRINK;
import static ts.andrey.common.rest.ServerEndpoint.NEW_MILK;
import static ts.andrey.common.rest.ServerEndpoint.NEW_ORDER;
import static ts.andrey.common.rest.ServerEndpoint.NEW_SYRUP;
import static ts.andrey.common.rest.ServerEndpoint.PASSWORD;
import static ts.andrey.common.rest.ServerEndpoint.SYRUP_LIST;
import static ts.andrey.common.rest.ServerEndpoint.UPDATE_INFO;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API)
public class ServerController {

    private final BaristaUserService baristaUserService;

    private final OrderService orderService;

    private final DrinkService drinkService;

    private final MilkService milkService;

    private final SyrupService syrupService;

    private final DessertService dessertService;

    private final NewOrderCreateService newOrderCreateService;

    private final OrderingDtoToOrderingMapper orderingDtoToOrderingMapper;

    private final OrderingToOutOrderingDtoMapper orderingToOutOrderingDtoMapper;

    @GetMapping(ALL_TODAY_ORDERS)
    public List<InOutOrderingDTO> getAllTodayOrders() {
        final var orderList = orderService.findToday();
        log.info("find all orders for today: {}", orderList.size());
        return orderList.stream().map(orderingToOutOrderingDtoMapper::mapToDto).toList();
    }

    @GetMapping(UPDATE_INFO)
    public List<NewOrderCreate> updateBarista() {
        return Collections.singletonList(newOrderCreateService.getNewOrderCreate());
    }

    @GetMapping(MAKE_UPDATE_FALSE)
    public List<String> makeUpdateFalse() {
        newOrderCreateService.makeFalse();
        return Collections.singletonList("OK");
    }

    @GetMapping(GET_ORDER_ID)
    public Ordering getOrder(@PathVariable int id) {
        return orderService.findOne(id);
    }

    @GetMapping(MILK_LIST)
    public List<Milk> getMilk() {
        return milkService.findAll();
    }

    @GetMapping(SYRUP_LIST)
    public List<Syrup> getSyrup() {
        return syrupService.findAll();
    }

    @GetMapping(DESSERT_LIST)
    public List<Dessert> getDessert() {
        return dessertService.findAll();
    }

    @GetMapping(DRINK_LIST)
    public List<Drink> getDrink() {
        return drinkService.findAll();
    }

    @PostMapping(NEW_ORDER)
    public ResponseEntity<Integer> newOrder(@RequestBody OrderingDTO orderDTO) {
        newOrderCreateService.makeTrue();
        final var order = orderService.save(orderingDtoToOrderingMapper.toOrdering(orderDTO,
                dessertService, milkService, syrupService, drinkService));
        log.info("create a new order with id: {}", orderDTO.getOrderId());
        return new ResponseEntity<>(order.getId(), HttpStatus.OK);
    }

    @PostMapping(CLOSE_ORDER)
    public ResponseEntity<HttpStatus> editOrder(@RequestBody OrderingDTO orderDTO) {
        orderService.update(true, LocalDateTime.now(), orderDTO.getOrderId());
        newOrderCreateService.makeTrue();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(NEW_MILK)
    public ResponseEntity<HttpStatus> newMilk(@RequestBody Milk milk) {
        milkService.save(milk);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(NEW_DRINK)
    public ResponseEntity<HttpStatus> newDrink(@RequestBody Drink drink) {
        drinkService.save(drink);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(NEW_SYRUP)
    public ResponseEntity<HttpStatus> newSyrup(@RequestBody Syrup syrup) {
        syrupService.save(syrup);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(NEW_DESSERT)
    public ResponseEntity<HttpStatus> newDessert(@RequestBody Dessert dessert) {
        dessertService.save(dessert);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping(PASSWORD)
    public String getPassword(@PathVariable String user) {
        log.info("get request for user: {}", user);
        return baristaUserService.findByName(user).getPassword();
    }

}
