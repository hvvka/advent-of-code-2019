package day9

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val program = Util().readFile("input.txt")

    val computer = IntcodeComputer(program, 2)
    computer.execute()
    println(computer.lastOutput) // 47253
}
