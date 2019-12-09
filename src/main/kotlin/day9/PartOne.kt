package day9

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val program = Util().readFile("input.txt")

    val computer = IntcodeComputer(program, 1)
    computer.execute()
    println(computer.lastOutput) // 3546494377
}
