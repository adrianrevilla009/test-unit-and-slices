package lab;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.LongRange;

class CartPropertyTest {

    @Property
    void nothingIsLost(@ForAll @LongRange(min = 0, max = 10_000_000) long total, @ForAll @IntRange(min = 1, max = 50) int parts) {
        assertThat(Cart.split(total, parts).stream().mapToLong(Long::longValue).sum()).isEqualTo(total);
    }

    @Property
    void partsDifferByAtMostOneCent(@ForAll @LongRange(min = 0, max = 10_000_000) long total, @ForAll @IntRange(min = 1, max = 50) int parts) {
        List<Long> split = Cart.split(total, parts);
        long min = split.stream().mapToLong(Long::longValue).min().orElseThrow();
        long max = split.stream().mapToLong(Long::longValue).max().orElseThrow();
        assertThat(max - min).isLessThanOrEqualTo(1);
    }
}
