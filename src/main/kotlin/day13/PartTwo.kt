package day13

import Util

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
fun main() {
    val input = Util().readFile("day13/input.txt")
    val quarters = 2
    val instructions = "${quarters}${input.substring(1)}"

    val arcadeCabinet = ArcadeCabinet(instructions)
    val result = arcadeCabinet.playForFree()
    println(result) // 13331
}
