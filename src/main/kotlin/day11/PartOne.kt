package day11

import Util
import day9.IntcodeComputer
import java.util.*

internal class EmergencyHullPaintingRobot(private val instructions: String) {

    val intcodeComputer = IntcodeComputer(this.instructions)

    private val paintedOnce: MutableSet<Point> = HashSet()

    private val whitePanels: MutableSet<Point> = HashSet()

    fun paintShip(startWhite: Boolean) {
        intcodeComputer.waitAfterOutputMode()
        var currentLocation = Robot(0, 0)
        var robotDirection = Direction.UP
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
    }

    fun getPanelsPaintedOnce(): Int = paintedOnce.size

    fun printRegistration() {
        val cornerX = whitePanels.minBy { (x, _) -> x }!!.x
        val cornerY = whitePanels.minBy { (_, y) -> y }!!.y

        whitePanels.forEach { it.x -= cornerX; it.y -= cornerY }

        val sizeX = whitePanels.maxBy { (x, _) -> x }!!.x + 1
        val sizeY = whitePanels.maxBy { (_, y) -> y }!!.y + 1
        val places = Array(sizeY) { IntArray(sizeX) }

        for ((x, y) in whitePanels) places[y][x] = 1

        for (i in places.size - 1 downTo 0) {
            places[i].forEach { panel -> if (panel == 1) print("█") else print(" ") }
            println()
        }
    }

    private fun turn(dir: Direction, right: Boolean): Direction {
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

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val input = Util().readFile("day11/input.txt")

    val emergencyHullPaintingRobot = EmergencyHullPaintingRobot(input)
    emergencyHullPaintingRobot.paintShip(false)
    val result = emergencyHullPaintingRobot.getPanelsPaintedOnce()
    println(result) // 2339
}

data class Robot(var x: Int, var y: Int)

typealias Point = Robot

enum class Direction {
    UP, RIGHT, DOWN, LEFT
}

