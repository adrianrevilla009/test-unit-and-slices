package lab;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id @GeneratedValue Long id;
    String customer;
    long totalCents;

    protected OrderEntity() {}

    public OrderEntity(String customer, long totalCents) {
        this.customer = customer;
        this.totalCents = totalCents;
    }

    public Long getId() { return id; }
    public String getCustomer() { return customer; }
    public long getTotalCents() { return totalCents; }
}
