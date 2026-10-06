package lab;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import lab.App.OrderController;
import lab.App.OrderDto;
import lab.App.OrderQueries;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

/** Slice: controllers, JSON, validation, advice. NOT services, repositories or DataSource. */
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ApplicationContext ctx;
    @MockBean OrderQueries queries;

    @Test
    void returnsJson() throws Exception {
        given(queries.find("o1")).willReturn(Optional.of(new OrderDto("o1", 4200)));
        mvc.perform(get("/orders/o1")).andExpect(status().isOk()).andExpect(jsonPath("$.totalCents").value(4200));
    }

    @Test
    void unknownOrderIs404() throws Exception {
        given(queries.find("nope")).willReturn(Optional.empty());
        mvc.perform(get("/orders/nope")).andExpect(status().isNotFound());
    }

    @Test
    void showsWhatTheSliceLoads() {
        assertThat(ctx.getBeanNamesForType(OrderController.class)).isNotEmpty();
        assertThat(ctx.getBeanNamesForType(AuditService.class)).as("@Service is not scanned").isEmpty();
        assertThat(ctx.containsBean("dataSource")).as("no data layer").isFalse();
        assertThat(ctx.getBeanNamesForType(MockMvc.class)).isNotEmpty();
    }
}
