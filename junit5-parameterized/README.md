# junit5-parameterized

JUnit 5 parameterized tests for a discount rule in `Discount.java`, using three different argument sources.

## Goal
Show how one test method can cover a whole table of cases, and which argument source fits which kind of data.

## Run it
```
cd junit5-parameterized
mvn -q -B test
```
Expected: no output and exit code 0. Surefire reports `Tests run: 12, Failures: 0, Errors: 0` for `DiscountTest` (6 CSV rows, 2 method-source rows, 3 enum values, 1 plain test).

## What it proves
- `csvTable` in `DiscountTest.java` checks six total/tier/expected rows, including the 10 000-cent threshold that adds 5 points and a rounded case (999 cents with SILVER gives 949).
- `methodSource` feeds larger orders from a `Stream<Arguments>`, and `neverIncreasesTotal` runs once per `Tier` value through `@EnumSource`.
- `rejectsNegative` shows that invalid input throws `IllegalArgumentException`, kept as a plain `@Test` because it has no table.

## Trade-offs
- Tables are compact, but a failing row only shows the display name, so the `name = "{1} on {0} cents -> {2}"` template matters.
- `@CsvSource` converts strings to types, which is convenient but hides type errors until run time.
- The expected values are written by hand next to the rule, so a wrong rule and a wrong table can agree.

## When not to use it
- When a case needs a long setup or its own assertions; a separate named test reads better.
- When you want to explore inputs you did not think of; use a property test (see `jqwik-property`).
