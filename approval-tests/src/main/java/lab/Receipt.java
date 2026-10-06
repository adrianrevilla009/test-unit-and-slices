package lab;

import java.util.List;
import java.util.Locale;

/** Orders domain: renders a plain-text receipt. Output shape is what the approval test pins down. */
public final class Receipt {
    public record Line(String name, int qty, long unitCents) {}

    private Receipt() {}

    public static String render(String orderId, List<Line> lines) {
        StringBuilder sb = new StringBuilder("RECEIPT ").append(orderId).append('\n');
        long total = 0;
        for (Line l : lines) {
            long sub = l.qty() * l.unitCents();
            total += sub;
            sb.append(String.format(Locale.ROOT, "%-10s %2d x %7s = %8s%n", l.name(), l.qty(), money(l.unitCents()), money(sub)));
        }
        sb.append(String.format(Locale.ROOT, "%-10s %24s%n", "TOTAL", money(total)));
        return sb.toString().replace("\r\n", "\n");
    }

    static String money(long cents) {
        return String.format(Locale.ROOT, "%d.%02d", cents / 100, cents % 100);
    }
}
