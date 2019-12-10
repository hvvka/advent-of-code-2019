package day10

import Util
import kotlin.math.atan2

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val input = Util().readFile("day10/input.txt")

    val asteroids = mutableListOf<Pair<Int, Int>>()
    input.split('\n').forEachIndexed { y, line ->
        line.trim().toCharArray().forEachIndexed { x, space ->
            if (space == '#') {
                asteroids.add(Pair(x, y))
            }
        }
    }

    val output = asteroids.map { (x1, y1) ->
        val angles = mutableSetOf<Double>()
        asteroids.forEach { (x2, y2) ->
            if (!(x1 == x2 && y1 == y2)) {
                angles.add(atan2((y2 - y1).toDouble(), (x2 - x1).toDouble()))
            }
        }
        Asteroid(x1, y1, angles.size)
    }.sortedByDescending { asteroid -> asteroid.neighbours }

    println(output[0]) // x=26, y=29, neighbours=299
}

data class Asteroid(val x: Int, val y: Int, val neighbours: Int)