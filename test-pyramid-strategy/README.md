# test-pyramid-strategy

A written test strategy for the Orders service (`STRATEGY.md`), its ratios and time budgets in `ratios.json`, and `check_ratios.py`, which checks them.

## Goal
Give a team a concrete starting point for how many tests to write at each layer, how long each layer may take, and which lab in this repo shows each layer.

## Run it
```
cd test-pyramid-strategy
python3 check_ratios.py
```
Expected: `ok: unit 70% / slice 20% / integration 8% / end_to_end 2%`. The script exits with a `FAIL:` message if the ratios do not sum to 100, do not shrink towards the top, or if slower layers do not get larger budgets.

## What it proves
- `ratios.json` holds 70/20/8/2 percent with budgets of 60, 120, 300 and 600 seconds, and the script validates those rules.
- `STRATEGY.md` maps the unit and slice layers to the labs in this repo; the integration and end-to-end layers have no lab here.
- It lists rules of thumb (push assertions down, fake owned collaborators) and anti-patterns (ice cream cone, hourglass).

## Trade-offs
- The numbers are a recommendation, not a measurement; the script only checks they are consistent, not that a real suite follows them.
- Counting tests by layer is crude, since one slow test can matter more than ten fast ones.
- The budgets are not enforced by any build step here; making them CI gates is left to the reader.

## When not to use it
- For a project with no tests yet, where writing the first tests matters more than the ratios.
- For an algorithm library or thin CRUD service, where the ratios the note itself says to adjust may not fit.
