package ts.andrey.IntegrationTests;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;
import ts.andrey.config.IntegrationTest;
import ts.andrey.dummy.DummyTDF;

import static org.junit.jupiter.api.Assertions.assertEquals;


@Sql(scripts = "/db/test_dump.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class СoffeeIT extends IntegrationTest {

    @Test
    void successCreateOrder() {
        //GIVEN
        final var request = DummyTDF.orderingDTO.getDefault();

        //WHEN
        final var response = sendPost("/api/newOrder", request, Integer.class);

        //THEN
        assertEquals(1, response.getBody());
    }

}
