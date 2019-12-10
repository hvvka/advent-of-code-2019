package day10

import Util
import kotlin.math.atan2
import kotlin.math.hypot

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

typealias Point = Pair<Int, Int>

fun main() {
    val input = Util().readFile("day10/input.txt")
    val stationCoordinates = Point(26, 29)

    var targets = mutableListOf<Target>()
    input.split('\n').forEachIndexed { y, line ->
        line.trim().toCharArray().forEachIndexed { x, space ->
            if (space == '#' && !(stationCoordinates.first == x && stationCoordinates.second == y)) {
                val deltaY = (y - stationCoordinates.second).toDouble()
                val deltaX = (x - stationCoordinates.first).toDouble()
                targets.add(
                    Target(x, y, atan2(deltaY, deltaX) * (180 / Math.PI), hypot(deltaX, deltaY))
                )
            }
        }
    }

    targets.sortBy { target -> target.degrees }
    val targetDegrees = targets.map { it.degrees }.toSet().toList()
    var currentDegrees = targetDegrees.indexOfFirst { it == -90.0 }
    var counter = 0

    while (targets.size != 0) {
        val target: Target? = targets.filter { target -> target.degrees == targetDegrees[currentDegrees] }
            .minBy { target -> target.distance }
        if (target != null) {
            targets = targets.filter { t -> !(t.x == target.x && t.y == target.y) }.toMutableList()
            if (++counter == 200) {
                println(target.x * 100 + target.y) // 1419
                break
            }
        }
        currentDegrees = if (currentDegrees < targetDegrees.size - 1) currentDegrees + 1 else 0
    }
}

data class Target(val x: Int, val y: Int, val degrees: Double, val distance: Double)