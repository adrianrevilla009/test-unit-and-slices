# datajpatest

A Spring Boot `@DataJpaTest` slice for `OrderRepository`, with a derived query, a JPQL aggregate and a test that lists which beans the slice loads.

## Goal
Show that the JPA slice starts only entities, repositories and an embedded database, with no web layer and no services.

## Run it
```
cd datajpatest
mvn -q -B test
```
Expected: exit code 0 and `Tests run: 3, Failures: 0` for `OrderRepositoryTest`; Hibernate prints its SQL (`select ... order by total_cents desc`, `insert into orders`) while the tests run.

## What it proves
- `derivedQueryOrdersByTotalDescending` persists three orders through `TestEntityManager` and checks `findByCustomerOrderByTotalCentsDesc("ana")` returns 900 then 500.
- `jpqlAggregate` checks the `@Query` in `OrderRepository.java`: `totalSpent("ana")` is 1400 and an unknown customer gives 0.
- `showsWhatTheSliceLoads` asserts a `dataSource` and the repository exist, while `AuditService` and `requestMappingHandlerMapping` do not.

## Trade-offs
- The embedded database is not your production database, so dialect-specific SQL can behave differently; use Testcontainers for that.
- Each test runs in a transaction that is rolled back, which keeps tests isolated but hides commit-time behaviour.
- The slice context takes several seconds to start, which counts against the slice budget in `test-pyramid-strategy`.

## When not to use it
- For queries that rely on database-specific features such as JSON operators or locking.
- For simple getters or mappings with no query; a unit test is enough.
