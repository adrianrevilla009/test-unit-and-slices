# webmvctest

A Spring Boot `@WebMvcTest` slice for a small `OrderController`, with a test that lists which beans the slice does and does not load.

## Goal
Show that a web slice starts only the web layer, so everything else the controller depends on has to be supplied as a mock.

## Run it
```
cd webmvctest
mvn -q -B test
```
Expected: exit code 0 and `Tests run: 3, Failures: 0` for `OrderControllerTest`; Spring logs a few INFO lines while the test context starts (about 5 seconds).

## What it proves
- `returnsJson` mocks `OrderQueries` with `@MockBean`, calls `GET /orders/o1` through `MockMvc` and checks status 200 and `$.totalCents` is 4200.
- `unknownOrderIs404` returns an empty `Optional` from the mock and gets 404.
- `showsWhatTheSliceLoads` asserts the controller and `MockMvc` exist, while `AuditService` (a top-level `@Service`) is absent and there is no `dataSource` bean.

## Trade-offs
- Services must be mocked, so the test checks the HTTP contract but not the real service behind it.
- Each distinct slice configuration creates its own Spring context, which costs seconds on first start.
- `@MockBean` is deprecated in newer Spring Boot versions; this lab uses Spring Boot 3.3.5, where it works.

## When not to use it
- When you need real security filters, serialization config or wiring across layers; use a full `@SpringBootTest`.
- For pure business rules, which a plain unit test covers much faster.
