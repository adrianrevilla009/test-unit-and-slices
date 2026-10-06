package lab;

import static org.assertj.core.api.Assertions.assertThat;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.LongRange;

/**
 * Fails on purpose (name does not match surefire's default includes, so `mvn test` skips it).
 * Run: mvn -q -B test -Dtest=ShrinkingDemo  -> jqwik reports the shrunk counterexample.
 */
class ShrinkingDemo {

    @Property
    void buggySplitLosesCents(@ForAll @LongRange(min = 0, max = 10_000_000) long total, @ForAll @IntRange(min = 1, max = 50) int parts) {
        assertThat(Cart.splitBuggy(total, parts).stream().mapToLong(Long::longValue).sum()).isEqualTo(total);
    }
}
