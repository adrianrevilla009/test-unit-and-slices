#!/usr/bin/env python3
"""Verify the recommended ratios: they sum to 100 and the pyramid narrows towards the top."""
import json
import pathlib
import sys

ORDER = ["unit", "slice", "integration", "end_to_end"]
data = json.loads((pathlib.Path(__file__).parent / "ratios.json").read_text())
pcts = [data[k]["percent"] for k in ORDER]
errors = []
if sum(pcts) != 100:
    errors.append(f"ratios sum to {sum(pcts)}, expected 100")
if pcts != sorted(pcts, reverse=True):
    errors.append(f"not a pyramid (should shrink towards the top): {pcts}")
budgets = [data[k]["runtime_budget_seconds"] for k in ORDER]
if budgets != sorted(budgets):
    errors.append(f"slower layers should get larger budgets: {budgets}")
if errors:
    sys.exit("FAIL: " + "; ".join(errors))
print("ok: " + " / ".join(f"{k} {data[k]['percent']}%" for k in ORDER))
