package day12

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val motionSimulator = MotionSimulator(moons)
    val result = motionSimulator.getStepsForCycle()
    println(result) // 314610635824376
}

