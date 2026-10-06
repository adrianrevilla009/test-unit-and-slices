package lab;

import java.util.ArrayList;
import java.util.List;

/** Orders domain: split a total (cents) across N payers with no cent lost. */
public final class Cart {
    private Cart() {}

    public static List<Long> split(long totalCents, int parts) {
        if (parts <= 0) throw new IllegalArgumentException("parts must be positive");
        if (totalCents < 0) throw new IllegalArgumentException("negative total");
        long base = totalCents / parts;
        long remainder = totalCents % parts;
        List<Long> out = new ArrayList<>();
        for (int i = 0; i < parts; i++) out.add(base + (i < remainder ? 1 : 0));
        return out;
    }

    /** Deliberately buggy variant used to demonstrate shrinking: drops the remainder. */
    public static List<Long> splitBuggy(long totalCents, int parts) {
        List<Long> out = new ArrayList<>();
        for (int i = 0; i < parts; i++) out.add(totalCents / parts);
        return out;
    }
}
