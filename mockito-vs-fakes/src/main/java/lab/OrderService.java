package lab;

import java.util.List;
import java.util.Optional;

/** Place and cancel orders against a repository and a payment gateway. */
public class OrderService {
    public record Order(String id, long totalCents, boolean paid, boolean cancelled) {}

    public interface OrderRepository {
        void save(Order order);
        Optional<Order> findById(String id);
        List<Order> findAll();
    }

    public interface PaymentGateway {
        boolean charge(String orderId, long cents);
    }

    private final OrderRepository repo;
    private final PaymentGateway payments;

    public OrderService(OrderRepository repo, PaymentGateway payments) {
        this.repo = repo;
        this.payments = payments;
    }

    public Order place(String id, long totalCents) {
        boolean paid = payments.charge(id, totalCents);
        Order order = new Order(id, totalCents, paid, false);
        repo.save(order);
        return order;
    }

    public void cancel(String id) {
        Order o = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("unknown order " + id));
        repo.save(new Order(o.id(), o.totalCents(), o.paid(), true));
    }

    public long openRevenue() {
        return repo.findAll().stream().filter(o -> o.paid() && !o.cancelled()).mapToLong(Order::totalCents).sum();
    }
}
