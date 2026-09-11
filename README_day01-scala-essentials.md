# Day 1 — Scala Essentials

Covers: `val`/`var`/`lazy val`, immutable collections, `for`-comprehension with `yield`, `List` vs `Vector` vs `Set` vs `Map`, traits, and a small student-grade processor.

## Concepts Practiced
- `val` (immutable) vs `var` (mutable) vs `lazy val` (evaluated on first access)
- `for`-comprehension with `yield` to filter/transform a list of students
- Comparing `List`, `Vector`, `Set`, and `Map`
- A `Logger` trait implemented by two different classes
- A small student-grade processor scenario using pure Scala collections

## How to Run
```bash
sbt run
```

## Code
> Note: this file's exact source wasn't shared in our chat (you wrote it yourself), so it isn't reproduced here. Your actual `src/main/scala/*.scala` file in this repo already has it — GitHub will show it under **Code** above. Feel free to paste it back to me if you want it embedded in this README too.

## Output
```
a=10, b=20
b after mutation=25
c before use (not yet evaluated)
Evaluating c
c=30
Students who passed with distinction: List(Ravi, Anita)
List: List(1, 2, 3) (ordered, fast prepend)
Vector: Vector(1, 2, 3) (fast random access + updates)
Set: Set(1, 2, 3) (no duplicates)
Map: Map(Ravi -> 88, Anita -> 92) (key-value lookup)
[LOG] Student grade processor started
[FILE-LOG] Student grade processor started
```

### Notes on the output
- `c before use...` prints **before** `c` is evaluated — proof that `lazy val` defers evaluation until first access.
- `Evaluating c` only appears once, even if `c` were accessed multiple times — a `lazy val` computes once and caches the result.
- `[LOG]` and `[FILE-LOG]` come from two separate classes both implementing the same `Logger` trait, demonstrating that a trait can be mixed into multiple unrelated classes.
