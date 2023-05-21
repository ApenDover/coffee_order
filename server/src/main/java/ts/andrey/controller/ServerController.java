package ts.andrey.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ts.andrey.common.dto.OrderingDTO;
import ts.andrey.common.dto.InOutOrderingDTO;
import ts.andrey.common.data.entity.Dessert;
import ts.andrey.common.data.entity.Drink;
import ts.andrey.common.data.entity.Milk;
import ts.andrey.common.data.entity.NewOrderCreate;
import ts.andrey.common.data.entity.Ordering;
import ts.andrey.common.data.entity.Syrup;
import ts.andrey.mapper.OrderingDtoToOrderingMapper;
import ts.andrey.mapper.OrderingToOutOrderingDtoMapper;
import ts.andrey.service.DessertService;
import ts.andrey.service.DrinkService;
import ts.andrey.service.MilkService;
import ts.andrey.service.NewOrderCreateService;
import ts.andrey.service.OrderService;
import ts.andrey.service.SyrupService;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ServerController {

    private final OrderService orderService;
    private final DrinkService drinkService;
    private final MilkService milkService;
    private final SyrupService syrupService;
    private final DessertService dessertService;
    private final NewOrderCreateService newOrderCreateService;

    @GetMapping("/getAllOrders")
    public List<InOutOrderingDTO> getAllOrders() {
        return orderService.findAll().stream().map(OrderingToOutOrderingDtoMapper.INSTANCE::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/getUpdateInfo")
    public List<NewOrderCreate> updateBarista() {
        return Collections.singletonList(newOrderCreateService.getNewOrderCreate());
    }

    @GetMapping("/makeUpdateFalse")
    public List<String> makeUpdateFalse() {
        newOrderCreateService.makeFalse();
        return Collections.singletonList("OK");
    }

    @GetMapping("/getOrder/{id}")
    public Ordering getOrder(@PathVariable int id) {
        return orderService.findOne(id);
    }

    @GetMapping("/getAllMilk")
    public List<Milk> getMilk() {
        return milkService.findAll();
    }

    @GetMapping("/getAllSyrup")
    public List<Syrup> getSyrup() {
        return syrupService.findAll();
    }

    @GetMapping("/getAllDessert")
    public List<Dessert> getDessert() {
        return dessertService.findAll();
    }

    @GetMapping("/getAllDrink")
    public List<Drink> getDrink() {
        return drinkService.findAll();
    }

    @PostMapping("/newOrder")
    public ResponseEntity<Integer> newOrder(@RequestBody OrderingDTO orderDTO) {
        newOrderCreateService.makeTrue();
        return new ResponseEntity<>(orderService.save(OrderingDtoToOrderingMapper.INSTANCE
                .toOrdering(orderDTO, dessertService, milkService, syrupService, drinkService)).getId(), HttpStatus.OK);
    }

    @PostMapping("/closeOrder")
    public ResponseEntity<HttpStatus> editOrder(@RequestBody OrderingDTO orderDTO) {
        orderService.update(true, new Date(), orderDTO.getOrderId());
        newOrderCreateService.makeTrue();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/newMilk")
    public ResponseEntity<HttpStatus> newMilk(@RequestBody Milk milk) {
        milkService.save(milk);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/newDrink")
    public ResponseEntity<HttpStatus> newDrink(@RequestBody Drink drink) {
        drinkService.save(drink);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/newSyrup")
    public ResponseEntity<HttpStatus> newSyrup(@RequestBody Syrup syrup) {
        syrupService.save(syrup);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/newDessert")
    public ResponseEntity<HttpStatus> newDessert(@RequestBody Dessert dessert) {
        dessertService.save(dessert);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
