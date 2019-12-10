package day9

import Util

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val program = Util().readFile("day9/input.txt")

    val computer = IntcodeComputer(program, 1)
    computer.execute()
    println(computer.lastOutput) // 3546494377
}
