# Test strategy note: Orders service

Ratios below are by number of tests and live in `ratios.json` (checked by `check_ratios.py`).

| Layer | Share | Budget | What belongs here | Lab in this repo |
|---|---|---|---|---|
| Unit | 70% | 60 s | Pure rules (discounts, splits), fakes for owned collaborators, property tests for invariants | `junit5-parameterized`, `mockito-vs-fakes`, `assertj-custom`, `jqwik-property` |
| Slice | 20% | 120 s | HTTP contract of controllers, JPA queries and mappings | `webmvctest`, `datajpatest` |
| Integration | 8% | 300 s | Real database/broker via Testcontainers, wiring of the full context | (other repos) |
| End to end | 2% | 600 s | One happy-path journey per critical flow | (other repos) |

## Rules of thumb

1. Push every assertion down to the cheapest layer that can detect the bug.
2. A bug found in a higher layer gets a regression test in the lowest layer that reproduces it.
3. Mock outbound side effects, fake owned stateful collaborators; never mock what you do not own.
4. Slice tests prove one layer; do not add `@MockBean`s until the slice stops starting.
5. Approval tests only for deterministic text/structured output, reviewed like code.
6. Budgets are CI gates: if a layer exceeds its budget, fix or split before adding tests.

## Anti-patterns

- Ice cream cone: many end-to-end tests, few unit tests (slow, flaky, vague failures).
- Hourglass: unit and e2e but nothing in the middle, so wiring bugs surface only at the top.
- Coverage as the goal: a high line percentage with no meaningful assertions.

The ratios are a starting point, not a law: a thin CRUD service may justify more slice tests, an algorithm library almost only unit and property tests.
