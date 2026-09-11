# Day 1 — Scala Essentials

## Task
Practice `val`/`var`/`lazy val`, immutable collections, a `for`-comprehension with `yield`, comparing `List`/`Vector`/`Set`/`Map`, a `Logger` trait implemented by two classes, and a small student-grade processor scenario.

## Code — `src/main/scala/Day01_ScalaEssentials.scala`
```scala
object Day01_ScalaEssentials {

  case class Student(name: String, marks: Int)

  trait Logger {
    def log(msg: String): Unit
  }

  class ConsoleLogger extends Logger {
    override def log(msg: String): Unit = println(s"[LOG] $msg")
  }

  class FileLogger extends Logger {
    override def log(msg: String): Unit = println(s"[FILE-LOG] $msg")
  }

  def main(args: Array[String]): Unit = {
    val a: Int = 10          // val - immutable
    var b: Int = 20          // var - mutable
    lazy val c: Int = { println("Evaluating c"); 30 } // evaluated on first use

    println(s"a=$a, b=$b")
    b += 5
    println(s"b after mutation=$b")
    println("c before use (not yet evaluated)")
    println(s"c=$c") // triggers evaluation here

    val students = List(Student("Ravi", 88), Student("Anita", 92), Student("Zoya", 75))

    val passed = for {
      s <- students
      if s.marks >= 80
    } yield s.name

    println(s"Students who passed with distinction: $passed")

    val listEx: List[Int] = List(1, 2, 3)
    val vectorEx: Vector[Int] = Vector(1, 2, 3)
    val setEx: Set[Int] = Set(1, 2, 2, 3)
    val mapEx: Map[String, Int] = Map("Ravi" -> 88, "Anita" -> 92)

    println(s"List: $listEx (ordered, fast prepend)")
    println(s"Vector: $vectorEx (fast random access + updates)")
    println(s"Set: $setEx (no duplicates)")
    println(s"Map: $mapEx (key-value lookup)")

    val loggers: List[Logger] = List(new ConsoleLogger, new FileLogger)
    loggers.foreach(_.log("Student grade processor started"))
  }
}
```

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

## Explanation — what's happening

**1. Trait + two classes (top of file)**
`Logger` is a contract: anything implementing it must have a `log(msg)` method. `ConsoleLogger` and `FileLogger` both implement it differently — this is polymorphism: the same interface, two behaviors.

**2. `val` / `var` / `lazy val`**
- `a` is immutable — can never be reassigned.
- `b` is mutable — `b += 5` changes it in place (20 → 25).
- `c` is `lazy val` — its body (`println("Evaluating c"); 30`) does **not** run when declared. It only runs the first time `c` is actually used. That's why `"c before use (not yet evaluated)"` prints *before* `"Evaluating c"` — proving the evaluation was deferred until `println(s"c=$c")` touched `c` for the first time.

**3. `for`-comprehension with `yield`**
```scala
for { s <- students; if s.marks >= 80 } yield s.name
```
This desugars to `students.withFilter(_.marks >= 80).map(_.name)`. Ravi (88) and Anita (92) pass the `>= 80` filter; Zoya (75) doesn't, so only `List(Ravi, Anita)` comes out.

**4. Collections comparison**
`Set(1, 2, 2, 3)` was written with a duplicate `2`, but a `Set` can never hold duplicates, so it silently collapses to `Set(1, 2, 3)` — this is the single most commonly-asked detail about this code in a viva.

**5. Trait polymorphism in action**
```scala
val loggers: List[Logger] = List(new ConsoleLogger, new FileLogger)
loggers.foreach(_.log("Student grade processor started"))
```
Both logger objects are stored as the common `Logger` type, and the same `.log(...)` call produces different output per object (`[LOG]` vs `[FILE-LOG]`) — this is why traits are useful: calling code doesn't need to know the concrete class.

## Viva Q&A
| Question | Answer |
|---|---|
| Why does `Set(1, 2, 2, 3)` print as `Set(1, 2, 3)`? | A `Set` cannot contain duplicate elements — the second `2` is silently dropped. |
| Where exactly does `lazy val c` get evaluated in this code? | At `println(s"c=$c")` — that's the first place `c` is referenced, so its body runs right there. |
| What does `_` mean in `loggers.foreach(_.log(...))`? | Shorthand for an anonymous function parameter — equivalent to `logger => logger.log(...)`. |
| Difference between a trait and an abstract class here? | A class can extend only one class but mix in multiple traits (`extends X with TraitA with TraitB`) — traits give Scala safe multiple inheritance of behavior. |
| Why use a `case class` for `Student`? | Free `toString`, `equals`/`hashCode`, and pattern-matching support, without writing boilerplate. |
