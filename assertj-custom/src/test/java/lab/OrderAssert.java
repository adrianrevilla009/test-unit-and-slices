package lab;

import org.assertj.core.api.AbstractAssert;

/** Domain-specific assertion: reads like the business rule and fails with a business message. */
public class OrderAssert extends AbstractAssert<OrderAssert, Order> {

    private OrderAssert(Order actual) {
        super(actual, OrderAssert.class);
    }

    public static OrderAssert assertThat(Order actual) {
        return new OrderAssert(actual);
    }

    public OrderAssert hasStatus(Order.Status expected) {
        isNotNull();
        if (actual.status() != expected) {
            failWithMessage("Expected order %s to be <%s> but was <%s>", actual.id(), expected, actual.status());
        }
        return this;
    }

    public OrderAssert hasTotalCents(long expected) {
        isNotNull();
        if (actual.totalCents() != expected) {
            failWithMessage("Expected order %s total <%d> but was <%d> (lines: %s)",
                    actual.id(), expected, actual.totalCents(), actual.lines());
        }
        return this;
    }

    public OrderAssert containsSku(String sku) {
        isNotNull();
        if (actual.lines().stream().noneMatch(l -> l.sku().equals(sku))) {
            failWithMessage("Expected order %s to contain sku <%s>", actual.id(), sku);
        }
        return this;
    }
}
