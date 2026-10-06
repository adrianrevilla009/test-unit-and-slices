package lab;

import java.util.Optional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    public record OrderDto(String id, long totalCents) {}

    /** A bean the web slice must NOT load: any test needing it has to supply a @MockBean. */
    @Service
    public static class OrderQueries {
        public Optional<OrderDto> find(String id) {
            return Optional.of(new OrderDto(id, 1000));
        }
    }

    @RestController
    public static class OrderController {
        private final OrderQueries queries;

        public OrderController(OrderQueries queries) {
            this.queries = queries;
        }

        @GetMapping("/orders/{id}")
        public OrderDto get(@PathVariable String id) {
            return queries.find(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
    }
}
