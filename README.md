# test-unit-and-slices

Eight small labs that show how to write fast, focused tests around a tiny Orders domain: parameterized and property tests, fakes versus mocks, custom assertions, Spring Boot test slices, approval tests and a test-pyramid strategy note.

## What is inside

| Folder | What it shows | Run |
| --- | --- | --- |
| [`junit5-parameterized`](./junit5-parameterized) | `@CsvSource`, `@MethodSource` and `@EnumSource` tests for a discount rule | `mvn -q -B test` |
| [`mockito-vs-fakes`](./mockito-vs-fakes) | An in-memory fake and Mockito mocks side by side, and when each fits | `mvn -q -B test` |
| [`assertj-custom`](./assertj-custom) | A domain-specific AssertJ assertion with business-language failure messages | `mvn -q -B test` |
| [`jqwik-property`](./jqwik-property) | Property tests for splitting a total, plus a deliberately failing demo that shows shrinking | `mvn -q -B test` |
| [`webmvctest`](./webmvctest) | What `@WebMvcTest` loads and what it leaves out | `mvn -q -B test` |
| [`datajpatest`](./datajpatest) | What `@DataJpaTest` loads: repository queries on an embedded database | `mvn -q -B test` |
| [`approval-tests`](./approval-tests) | A hand-written approval helper that pins a text receipt to a file | `mvn -q -B test` |
| [`test-pyramid-strategy`](./test-pyramid-strategy) | A written test strategy with recommended ratios and a script that checks them | `python3 check_ratios.py` |

## Prerequisites

- Java 21 and Maven 3.8 or newer (the Spring folders use Spring Boot 3.3.5).
- Python 3 for `test-pyramid-strategy`.
- Network access on the first run so Maven can download dependencies.

## How to read it

Start with `junit5-parameterized`, then `mockito-vs-fakes`, and finish the unit layer with `assertj-custom` and `jqwik-property`. Next read `webmvctest` and `datajpatest` for the slice layer, and end with `test-pyramid-strategy`, which explains how the labs fit together. Each Maven folder is standalone: run the command from inside it.
