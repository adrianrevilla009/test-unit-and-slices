package lab;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import lab.Discount.Tier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

class DiscountTest {

    @ParameterizedTest(name = "{1} on {0} cents -> {2}")
    @CsvSource({
        "1000, NONE,   1000",
        "1000, SILVER,  950",
        "1000, GOLD,    900",
        "10000, NONE,  9500",
        "10000, GOLD,  8500",
        "999,  SILVER,  949",
    })
    void csvTable(long total, Tier tier, long expected) {
        assertThat(Discount.apply(total, tier)).isEqualTo(expected);
    }

    static Stream<Arguments> bigOrders() {
        return Stream.of(Arguments.of(20_000L, Tier.SILVER, 18_000L), Arguments.of(50_000L, Tier.GOLD, 42_500L));
    }

    @ParameterizedTest
    @MethodSource("bigOrders")
    void methodSource(long total, Tier tier, long expected) {
        assertThat(Discount.apply(total, tier)).isEqualTo(expected);
    }

    @ParameterizedTest
    @EnumSource(Tier.class)
    void neverIncreasesTotal(Tier tier) {
        assertThat(Discount.apply(5_000, tier)).isLessThanOrEqualTo(5_000);
    }

    @Test
    void rejectsNegative() {
        assertThatThrownBy(() -> Discount.apply(-1, Tier.NONE)).isInstanceOf(IllegalArgumentException.class);
    }
}
