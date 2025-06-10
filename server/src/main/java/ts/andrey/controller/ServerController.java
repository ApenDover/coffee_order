package ts.andrey.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ts.andrey.mapper.DtoEntityMapper;
import ts.andrey.mapper.OrderingDtoToOrderingMapper;
import ts.andrey.mapper.OrderingToOutOrderingDtoMapper;
import ts.andrey.server.api.ApiApi;
import ts.andrey.server.model.CafeOrderDto;
import ts.andrey.server.model.DessertDto;
import ts.andrey.server.model.DrinkDto;
import ts.andrey.server.model.InOutOrderingDTO;
import ts.andrey.server.model.MilkDto;
import ts.andrey.server.model.NewOrderCreateDto;
import ts.andrey.server.model.OrderingDTO;
import ts.andrey.server.model.SyrupDto;
import ts.andrey.service.BaristaUserService;
import ts.andrey.service.DessertService;
import ts.andrey.service.DrinkService;
import ts.andrey.service.MilkService;
import ts.andrey.service.NewOrderCreateService;
import ts.andrey.service.OrderService;
import ts.andrey.service.SyrupService;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class ServerController implements ApiApi {

    private final BaristaUserService baristaUserService;

    private final OrderService orderService;

    private final DrinkService drinkService;

    private final MilkService milkService;

    private final SyrupService syrupService;

    private final DessertService dessertService;

    private final NewOrderCreateService newOrderCreateService;

    private final OrderingDtoToOrderingMapper orderingDtoToOrderingMapper;

    private final OrderingToOutOrderingDtoMapper orderingToOutOrderingDtoMapper;

    private final DtoEntityMapper dtoEntityMapper;


    @Override
    public ResponseEntity<List<InOutOrderingDTO>> getAllOrders() {
        final var orderList = orderService.findAll();
        log.info("find all orders: {}", orderList.size());
        final var response = orderList.stream()
                .map(orderingToOutOrderingDtoMapper::mapToDto)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<InOutOrderingDTO>> getAllTodayOrders() {
        final var orderList = orderService.findToday();
        log.info("find all orders for today: {}", orderList.size());
        final var response = orderList.stream()
                .map(orderingToOutOrderingDtoMapper::mapToDto)
                .toList();
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<NewOrderCreateDto>> updateBarista() {
        final var response = Collections.singletonList(newOrderCreateService.getNewOrderCreate());
        return ResponseEntity.ok().body(response);
    }

    @Override
    public ResponseEntity<List<String>> makeUpdateFalse() {
        newOrderCreateService.makeFalse();
        return ResponseEntity.ok().body(Collections.singletonList("OK"));
    }

    @Override
    public ResponseEntity<CafeOrderDto> getOrder(Integer id) {
        final var response = orderService.findOne(id);
        final var responseDto = dtoEntityMapper.mapCafeOrder(response);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<List<MilkDto>> getMilk() {
        final var response = milkService.findAll();
        final var responseDto = dtoEntityMapper.mapMilkDto(response);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<List<SyrupDto>> getSyrup() {
        final var response = syrupService.findAll();
        final var responseDto = dtoEntityMapper.mapSyrup(response);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<List<DessertDto>> getDessert() {
        final var response = dessertService.findAll();
        final var responseDto = dtoEntityMapper.mapDessert(response);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<List<DrinkDto>> getDrink() {
        final var response = drinkService.findAll();
        final var responseDto = dtoEntityMapper.mapDrinkDto(response);
        return ResponseEntity.ok().body(responseDto);
    }

    @Override
    public ResponseEntity<Integer> newOrder(@RequestBody OrderingDTO orderDTO) {
        newOrderCreateService.makeTrue();
        final var cafeOrder = orderingDtoToOrderingMapper.toOrdering(orderDTO, dessertService, milkService, syrupService, drinkService);
        final var order = orderService.save(cafeOrder);
        log.info("create a new order with id: {}", order.getId());
        return new ResponseEntity<>(order.getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> editOrder(@RequestBody OrderingDTO orderDTO) {
        orderService.update(true, OffsetDateTime.now(), orderDTO.getOrderId());
        newOrderCreateService.makeTrue();
        return ResponseEntity.ok().body("Ok");
    }

    @Override
    public ResponseEntity<String> newMilk(@RequestBody MilkDto milkDto) {
        final var milkEntity = dtoEntityMapper.mapMilkDto(milkDto);
        milkService.save(milkEntity);
        return ResponseEntity.ok().body("Ok");
    }

    @Override
    public ResponseEntity<String> newDrink(@RequestBody DrinkDto drinkDto) {
        final var drinkEntity = dtoEntityMapper.mapDrinkDto(drinkDto);
        drinkService.save(drinkEntity);
        return ResponseEntity.ok().body("Ok");
    }

    @Override
    public ResponseEntity<String> newSyrup(@RequestBody SyrupDto syrupDto) {
        final var syrupEntity = dtoEntityMapper.mapSyrupDto(syrupDto);
        syrupService.save(syrupEntity);
        return ResponseEntity.ok().body("Ok");
    }

    @Override
    public ResponseEntity<String> newDessert(@RequestBody DessertDto dessertDto) {
        final var dessertEntity = dtoEntityMapper.mapDessertDto(dessertDto);
        dessertService.save(dessertEntity);
        return ResponseEntity.ok().body("Ok");
    }

    @Override
    public ResponseEntity<String> getPassword(String user) {
        log.info("get request for user: {}", user);
        final var response = baristaUserService.findByName(user).getPassword();
        return ResponseEntity.ok().body(response);
    }

}
