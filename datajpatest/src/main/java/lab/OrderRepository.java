package lab;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerOrderByTotalCentsDesc(String customer);

    @Query("select coalesce(sum(o.totalCents), 0) from OrderEntity o where o.customer = ?1")
    long totalSpent(String customer);
}
