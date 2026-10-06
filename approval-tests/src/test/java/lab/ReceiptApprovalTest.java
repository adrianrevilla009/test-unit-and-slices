package lab;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import lab.Receipt.Line;
import org.junit.jupiter.api.Test;

class ReceiptApprovalTest {

    private static final List<Line> LINES = List.of(new Line("book", 2, 1500), new Line("pen", 10, 99));

    @Test
    void receiptMatchesApprovedFile() {
        Approvals.verify("receipt", Receipt.render("o-42", LINES));
    }

    @Test
    void anyChangeInOutputIsCaught() {
        assertThatThrownBy(() -> Approvals.verify("receipt", Receipt.render("o-42", List.of(new Line("book", 3, 1500)))))
                .isInstanceOf(AssertionError.class)
                .hasMessageContaining("differs");
    }
}
