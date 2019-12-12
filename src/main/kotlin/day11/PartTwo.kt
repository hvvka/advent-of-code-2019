package day11

import Util


/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val input = Util().readFile("day11/input.txt")

    val intcodeComputer = EmergencyHullPaintingRobot(input)
    intcodeComputer.paintShip(true)
    intcodeComputer.printRegistration() // PGUEPLPR
}

