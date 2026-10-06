package lab;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lab.OrderService.Order;
import lab.OrderService.OrderRepository;
import lab.OrderService.PaymentGateway;
import org.junit.jupiter.api.Test;

/**
 * Guideline: FAKE what you own and what has state (the repository: behaviour is verified through outcomes);
 * MOCK what is an outbound side effect with no observable state (the gateway: verify the interaction).
 */
class OrderServiceTest {

    /** In-memory fake: real behaviour, no framework, reusable across tests. */
    static class FakeOrderRepository implements OrderRepository {
        private final List<Order> store = new ArrayList<>();

        @Override public void save(Order o) {
            store.removeIf(x -> x.id().equals(o.id()));
            store.add(o);
        }
        @Override public Optional<Order> findById(String id) {
            return store.stream().filter(x -> x.id().equals(id)).findFirst();
        }
        @Override public List<Order> findAll() { return List.copyOf(store); }
    }

    @Test
    void fakeStyle_assertsOnOutcome_survivesRefactoring() {
        var repo = new FakeOrderRepository();
        var service = new OrderService(repo, (id, cents) -> true);

        service.place("o1", 500);
        service.place("o2", 700);
        service.cancel("o1");

        assertThat(service.openRevenue()).isEqualTo(700);
    }

    @Test
    void mockStyle_assertsOnInteraction_breaksIfImplementationChanges() {
        OrderRepository repo = mock(OrderRepository.class);
        when(repo.findById("o1")).thenReturn(Optional.of(new Order("o1", 500, true, false)));
        var service = new OrderService(repo, (id, cents) -> true);

        service.cancel("o1");

        // Coupled to *how* cancel works (one save call with this exact record).
        verify(repo).save(new Order("o1", 500, true, true));
    }

    @Test
    void mockIsRightForOutboundSideEffects() {
        PaymentGateway gateway = mock(PaymentGateway.class);
        when(gateway.charge(any(), any(Long.class))).thenReturn(false);
        var service = new OrderService(new FakeOrderRepository(), gateway);

        Order order = service.place("o9", 1200);

        verify(gateway).charge("o9", 1200L);
        assertThat(order.paid()).isFalse();
    }
}
