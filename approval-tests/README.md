# approval-tests

An approval test for the plain-text receipt rendered by `Receipt.java`, using a small hand-written helper, `Approvals.java`, instead of a library.

## Goal
Show how to pin a whole text output to a reviewed file so any change in the output fails the test and shows up in a diff.

## Run it
```
cd approval-tests
mvn -q -B test
```
Expected: exit code 0 and `Tests run: 2, Failures: 0` for `ReceiptApprovalTest`. To accept an intended change, run `mvn -q -B test -Dapprove=true`, then review the git diff of `src/test/resources/lab/receipt.approved.txt`.

## What it proves
- `receiptMatchesApprovedFile` renders order `o-42` with two lines and compares it with `receipt.approved.txt` (total `39.90`).
- On a mismatch `Approvals` writes `target/approvals/receipt.received.txt` and fails with a message containing `differs` and the received text.
- `anyChangeInOutputIsCaught` shows this by changing the order and expecting the failure.

## Trade-offs
- Approved files get approved without reading; they need to be reviewed like code.
- The helper is minimal: exact string comparison, no diff tool launch and no scrubbing of dates or ids.
- Output with timestamps or random ids is not stable, so it must be normalized first.

## When not to use it
- For small values where a direct `isEqualTo` is clearer.
- For output that changes on every run and cannot be made deterministic.
