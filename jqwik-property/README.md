# jqwik-property

Property-based tests with jqwik for `Cart.split`, which divides a total in cents across N payers, plus a failing demo against a buggy variant.

## Goal
Show how stating an invariant lets the framework find and shrink counterexamples, instead of listing examples by hand.

## Run it
```
cd jqwik-property
mvn -q -B test
mvn -q -B test -Dtest=ShrinkingDemo
```
Expected: the first command exits 0 with `Tests run: 2, Failures: 0` for `CartPropertyTest`; jqwik prints its seed and edge-case counts. The second command fails on purpose, and jqwik reports a shrunk sample for `buggySplitLosesCents`.

## What it proves
- `nothingIsLost` holds for totals up to 10 000 000 cents and 1 to 50 parts: the pieces always sum to the total.
- `partsDifferByAtMostOneCent` holds for the same range, so the remainder is spread fairly.
- `ShrinkingDemo` runs `Cart.splitBuggy`, which drops the remainder, and jqwik reduces the failing input to a small case. Its name does not match Surefire's default includes, so `mvn test` skips it and only `-Dtest=ShrinkingDemo` runs it.

## Trade-offs
- Random generation can miss rare cases, and a failing run needs the printed seed to reproduce. A `.jqwik-database` file in the folder records past failures.
- Properties are harder to write than examples; they must state a rule rather than a value.
- The default of 1000 tries per property adds time compared with a plain unit test.

## When not to use it
- When the behaviour has no useful invariant, such as fixed-format output; use an example or approval test.
- When each run is expensive (database, network); the many tries would be too slow.
