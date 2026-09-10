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
