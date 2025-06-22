package ts.andrey.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ts.andrey.dummy.DummyTDF;
import ts.andrey.entity.Dessert;
import ts.andrey.entity.Drink;
import ts.andrey.entity.Milk;
import ts.andrey.entity.Syrup;
import ts.andrey.server.model.OrderingDTO;
import ts.andrey.service.DessertService;
import ts.andrey.service.DrinkService;
import ts.andrey.service.MilkService;
import ts.andrey.service.OrderService;
import ts.andrey.service.SyrupService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderingDtoToOrderingMapperTest {

    public static final OrderingDtoToOrderingMapper MAPPER = Mappers.getMapper(OrderingDtoToOrderingMapper.class);

    @Mock
    OrderService orderService;

    @Mock
    DrinkService drinkService;

    @Mock
    MilkService milkService;

    @Mock
    SyrupService syrupService;

    @Mock
    DessertService dessertService;

    @BeforeEach
    void setUpMockito() {
        when(drinkService.findOne(anyInt())).thenReturn(new Drink().setPrice(10));
        when(milkService.findOne(anyInt())).thenReturn(new Milk().setPrice(10));
        when(syrupService.findOne(anyInt())).thenReturn(new Syrup().setPrice(10));
        when(dessertService.findOne(anyInt())).thenReturn(new Dessert().setPrice(10));
    }

    @Test
    void mapperTest() {
        final var dto = new OrderingDTO();
        dto.setComment("comment");
        dto.setDessertsId(List.of(1, 2, 3));
        dto.setDrinkId(5);
        dto.setMilkId(1);
        dto.setSyrupId(4);
        final var actual = MAPPER.toOrdering(dto, dessertService, milkService, syrupService, drinkService);
        assertNotNull(actual.getPrice());
        assertNotNull(actual.getStatus());
    }

    @Test
    void mapperTestEmptyDto() {
        final var dto = DummyTDF.orderingDTO.getDefault();
        final var actual = MAPPER.toOrdering(dto, dessertService, milkService, syrupService, drinkService);
        assertEquals(60, actual.getPrice());
        assertEquals("comment", actual.getComment());
        assertEquals(3, actual.getDesserts().size());
        assertTrue(actual.getStatus());
        assertNotNull(actual.getDate());
        assertNotNull(actual.getMilk());
        assertNotNull(actual.getSyrup());
        assertNotNull(actual.getDrink());
        assertNull(actual.getDateReady());
    }

}
