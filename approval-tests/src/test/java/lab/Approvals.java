package lab;

import static org.assertj.core.api.Assertions.fail;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Minimal approval-testing helper (same idea as the ApprovalTests library, no dependency).
 * Compares output with src/test/resources/lab/<name>.approved.txt. On mismatch it writes
 * target/approvals/<name>.received.txt and fails. Re-approve with: -Dapprove=true.
 */
final class Approvals {
    private Approvals() {}

    static void verify(String name, String actual) {
        Path approved = Path.of("src/test/resources/lab", name + ".approved.txt");
        Path received = Path.of("target/approvals", name + ".received.txt");
        try {
            if (Boolean.getBoolean("approve")) {
                Files.createDirectories(approved.getParent());
                Files.writeString(approved, actual);
                return;
            }
            String expected = Files.exists(approved) ? Files.readString(approved) : "<no approved file>";
            if (!expected.equals(actual)) {
                Files.createDirectories(received.getParent());
                Files.writeString(received, actual);
                fail("Output differs from %s. Diff with %s; if the change is intended, run with -Dapprove=true.%n--- received ---%n%s",
                        approved, received, actual);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
