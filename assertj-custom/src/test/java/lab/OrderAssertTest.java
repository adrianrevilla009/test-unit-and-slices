package lab;

import static lab.OrderAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lab.Order.Line;
import lab.Order.Status;
import org.junit.jupiter.api.Test;

class OrderAssertTest {

    private final Order order =
            new Order("o1", Status.PAID, List.of(new Line("book", 2, 1500), new Line("pen", 1, 200)));

    @Test
    void fluentDomainAssertions() {
        assertThat(order).hasStatus(Status.PAID).hasTotalCents(3200).containsSku("pen");
    }

    @Test
    void failureMessageIsInBusinessLanguage() {
        assertThatThrownBy(() -> assertThat(order).hasStatus(Status.SHIPPED))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("Expected order o1 to be <SHIPPED> but was <PAID>");
    }

    @Test
    void totalMismatchShowsLines() {
        assertThatThrownBy(() -> assertThat(order).hasTotalCents(1))
                .hasMessageContaining("total <1> but was <3200>")
                .hasMessageContaining("book");
    }

    @Test
    void nullActualFailsCleanly() {
        assertThatThrownBy(() -> assertThat(null).hasStatus(Status.NEW)).isInstanceOf(AssertionError.class);
    }
}
