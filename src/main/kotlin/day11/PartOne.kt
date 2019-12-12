package day11

import Util
import day9.IntcodeComputer
import java.util.*
import java.util.function.Consumer

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val input = Util().readFile("day11/input.txt")

    val emergencyHullPaintingRobot = EmergencyHullPaintingRobot(input)
    println(emergencyHullPaintingRobot.paintShip(false))
}

internal class EmergencyHullPaintingRobot(val instructions: String) {

    val ship: MutableList<Point> = mutableListOf()

    val robot: Robot = Robot(0, 0)

    var input = 0

    fun paintShip(startWhite: Boolean): Any? {
        val intcodeComputer = IntcodeComputer(this.instructions)
        intcodeComputer.waitAfterOutputMode()
        var currentLocation = Point(0, 0)
        var robotDirection = Direction.UP
        val paintedOnce: MutableSet<Point> = HashSet()
        val whitePanels: MutableSet<Point> = HashSet()
        if (startWhite) whitePanels.add(currentLocation)
        while (!intcodeComputer.isTerminated) {
            intcodeComputer.addInput(if (whitePanels.contains(currentLocation)) 1 else 0)
            intcodeComputer.execute()
            intcodeComputer.execute()
            val output = intcodeComputer.getOutput()
            val paintColor: Int = output[output.size - 2].toInt()
            val newDirection: Int = output[output.size - 1].toInt()
            paintedOnce.add(currentLocation)
            when (paintColor) {
                1 -> whitePanels.add(currentLocation)
                0 -> whitePanels.remove(currentLocation)
            }
            robotDirection = turn(robotDirection, newDirection == 1)
            currentLocation = move(currentLocation, robotDirection)
        }
        return if (startWhite) constructImage(whitePanels) else paintedOnce.size
    }

    private fun constructImage(whitePlaces: Set<Point>): Array<IntArray> {
        val cornerX = whitePlaces.stream().mapToInt { (x) -> x }.min().asInt
        val cornerY = whitePlaces.stream().mapToInt { (_, y) -> y }.min().asInt
        whitePlaces.forEach(Consumer { e: Point -> e.x = e.x - cornerX; e.y = e.y - cornerY })
        val sizex = whitePlaces.stream().mapToInt { e -> e.x }.max().asInt + 1
        val sizey = whitePlaces.stream().mapToInt { e -> e.y }.max().asInt + 1
        val places = Array(sizey) { IntArray(sizex) }
        for ((x, y) in whitePlaces) places[y][x] = 1
        return places
    }

    fun turn(dir: Direction, right: Boolean): Direction {
        var cur = dir.ordinal + if (right) 1 else -1
        if (cur == Direction.values().size) cur = 0 else if (cur == -1) cur = 3
        return Direction.values()[cur]
    }

    private fun move(robot: Robot, direction: Direction): Point = when (direction) {
        Direction.UP -> Point(robot.x, robot.y + 1)
        Direction.DOWN -> Point(robot.x, robot.y - 1)
        Direction.RIGHT -> Point(robot.x + 1, robot.y)
        Direction.LEFT -> Point(robot.x - 1, robot.y)
    }
}

data class Robot(var x: Int, var y: Int)

typealias Point = Robot

enum class Direction {
    UP, RIGHT, DOWN, LEFT
}

