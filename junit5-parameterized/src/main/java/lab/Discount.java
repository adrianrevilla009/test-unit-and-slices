package lab;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Orders domain: discount rule by order total (in cents) and loyalty tier. */
public final class Discount {
    public enum Tier { NONE, SILVER, GOLD }

    private Discount() {}

    public static long apply(long totalCents, Tier tier) {
        if (totalCents < 0) throw new IllegalArgumentException("negative total");
        int percent = switch (tier) {
            case NONE -> 0;
            case SILVER -> 5;
            case GOLD -> 10;
        };
        if (totalCents >= 10_000) percent += 5;
        BigDecimal off = BigDecimal.valueOf(totalCents).multiply(BigDecimal.valueOf(percent))
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
        return totalCents - off.longValue();
    }
}
