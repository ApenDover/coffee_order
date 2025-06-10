package ts.andrey.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ts.andrey.feign.ServerClient;
import ts.andrey.model.CafeOrderDto;
import ts.andrey.model.DessertDto;
import ts.andrey.model.DrinkDto;
import ts.andrey.model.InOutOrderingDTO;
import ts.andrey.model.MilkDto;
import ts.andrey.model.NewOrderCreateDto;
import ts.andrey.model.OrderingDTO;
import ts.andrey.model.SyrupDto;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetApi {

    private final ServerClient serverClient;

    public Integer newOrder(OrderingDTO orderingDTO) {
        final var response = serverClient.newOrder(orderingDTO);
        return response.getBody();
    }

    public String editOrder(OrderingDTO orderingDTO) {
        final var response = serverClient.editOrder(orderingDTO);
        return response.getBody();
    }

    public List<NewOrderCreateDto> updateBarista() {
        final var response = serverClient.updateBarista();
        return response.getBody();
    }

    public List<String> makeUpdateFalse() {
        final var response = serverClient.makeUpdateFalse();
        return response.getBody();
    }


    public String getPassword(String userName) {
        final var response = serverClient.getPassword(userName);
        return response.getBody();
    }

    public List<MilkDto> getMilkList() {
        final var response = serverClient.getMilk();
        return response.getBody();
    }

    public List<InOutOrderingDTO> getOrderList() {
        final var response = serverClient.getAllOrders();
        return response.getBody();
    }

    public List<InOutOrderingDTO> getTodayOrderList() {
        final var response = serverClient.getAllTodayOrders();
        return response.getBody();
    }

    public CafeOrderDto getOrder(int id) {
        final var response = serverClient.getOrder(id);
        return response.getBody();
    }

    public List<DrinkDto> getDrinkList() {
        final var response = serverClient.getDrink();
        return response.getBody();
    }

    public List<SyrupDto> getSyrupList() {
        final var response = serverClient.getSyrup();
        return response.getBody();
    }

    public List<DessertDto> getDessertList() {
        final var response = serverClient.getDessert();
        return response.getBody();
    }

}
