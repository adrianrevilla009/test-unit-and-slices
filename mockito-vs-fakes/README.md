# mockito-vs-fakes

One `OrderService` tested three ways: with an in-memory fake repository, with a Mockito mock of the same repository, and with a Mockito mock of a payment gateway.

## Goal
Show why a fake is the better choice for a stateful collaborator you own, and why a mock is the right tool for an outbound side effect.

## Run it
```
cd mockito-vs-fakes
mvn -q -B test
```
Expected: exit code 0 and `Tests run: 3, Failures: 0` for `OrderServiceTest`. Mockito prints a harmless warning that it is self-attaching its agent.

## What it proves
- `fakeStyle_assertsOnOutcome_survivesRefactoring` places two orders, cancels one and asserts only on `openRevenue()` (700). It does not care how the service calls the repository.
- `mockStyle_assertsOnInteraction_breaksIfImplementationChanges` verifies one exact `save` call with one exact record, so it breaks if `cancel` is reimplemented differently.
- `mockIsRightForOutboundSideEffects` stubs `PaymentGateway.charge` to return false, checks the call with `verify` and asserts the order comes back unpaid.

## Trade-offs
- A fake (`FakeOrderRepository` in the test file) is real code that must be kept in line with the interface and can contain its own bugs.
- Mock-based tests are quick to write but are coupled to the implementation.
- The fake is defined inside the test class here; sharing it across classes would need a test-jar or a shared test source set.

## When not to use it
- Do not write a fake for a dependency with no state or logic worth reproducing; a one-line stub or lambda is enough.
- Do not mock types you do not own when a fake or a real in-memory version exists.
