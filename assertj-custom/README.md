# assertj-custom

A custom AssertJ assertion, `OrderAssert`, for an `Order` record with line items and a status.

## Goal
Show how a domain-specific assertion makes tests read like business rules and makes failures explain themselves.

## Run it
```
cd assertj-custom
mvn -q -B test
```
Expected: exit code 0 and `Tests run: 4, Failures: 0` for `OrderAssertTest`.

## What it proves
- `OrderAssert.java` extends `AbstractAssert` and offers `hasStatus`, `hasTotalCents` and `containsSku`, so a test can chain `assertThat(order).hasStatus(PAID).hasTotalCents(3200).containsSku("pen")`.
- A wrong status fails with `Expected order o1 to be <SHIPPED> but was <PAID>`, and a wrong total also prints the order lines.
- Asserting on a `null` order fails with an `AssertionError` instead of a `NullPointerException`.

## Trade-offs
- Each assertion is extra code to maintain, and in this lab it lives in the test source tree of the same module.
- Hand-written entry points do not plug into AssertJ's static `Assertions.assertThat`, so tests must import `OrderAssert.assertThat` and can clash with the standard import.
- AssertJ's assertion generator could produce this code, but it is not used here.

## When not to use it
- For a type used in only one or two tests, plain `assertThat(...).extracting(...)` is shorter.
- When the failure messages from standard assertions are already clear enough.
