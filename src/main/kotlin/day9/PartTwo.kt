package day9

import Util

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val program = Util().readFile("day9/sinput.txt")

    val computer = IntcodeComputer(program, 2)
    computer.execute()
    println(computer.lastOutput) // 47253
}
