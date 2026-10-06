package lab;

import java.util.List;

/** Orders domain: an order with line items and a status. */
public record Order(String id, Status status, List<Line> lines) {
    public enum Status { NEW, PAID, SHIPPED, CANCELLED }

    public record Line(String sku, int qty, long unitCents) {}

    public long totalCents() {
        return lines.stream().mapToLong(l -> l.qty() * l.unitCents()).sum();
    }
}
