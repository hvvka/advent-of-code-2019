package day12

import Util
import kotlin.math.abs

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
internal val moons = listOf(
    Moon(Gravity(-14, -4, -11)),
    Moon(Gravity(-9, 6, -7)),
    Moon(Gravity(4, 1, 4)),
    Moon(Gravity(2, -14, -9))
)

fun main() {
    val motionSimulator = MotionSimulator(moons)
    motionSimulator.simulate(1000)
    val result = motionSimulator.calculateTotalEnergyInTheSystem()
    println(result) // 10028
}

internal class MotionSimulator(private var moons: List<Moon>) {

    private val initialMoonsState = moons.map { Moon(it.gravity.copy()) }.toList()

    fun simulate(steps: Int) {
        for (i in 0 until steps) {
            doStep()
        }
    }

    private fun doStep() {
        forEachPair { j: Int, k: Int ->
            moons[j].applyVelocity(moons[k])
            moons[k].applyVelocity(moons[j])
        }
        moons.forEach { it.updateGravity() }
    }

    fun getStepsForCycle(): Long {
        val initialGravityState = moons.map { it.gravity.copy() }.toTypedArray()

        val initialXState = initialGravityState.map { it.x }.toIntArray()
        val initialYState = initialGravityState.map { it.y }.toIntArray()
        val initialZState = initialGravityState.map { it.z }.toIntArray()

        val hasSameXState =
            { -> !moons.map { it.gravity }.toTypedArray().map { it.x }.toIntArray().contentEquals(initialXState) }
        val hasSameYState =
            { -> !moons.map { it.gravity }.toTypedArray().map { it.y }.toIntArray().contentEquals(initialYState) }
        val hasSameZState =
            { -> !moons.map { it.gravity }.toTypedArray().map { it.z }.toIntArray().contentEquals(initialZState) }

        val steps = mutableListOf(1L, 1L, 1L)
        do {
            doStep()
            steps[0] += 1L
        } while (hasSameXState())
        this.moons = setMoonsToInitialState()
        do {
            doStep()
            steps[1] += 1L
        } while (hasSameYState())
        this.moons = setMoonsToInitialState()
        do {
            doStep()
            steps[2] += 1L
        } while (hasSameZState())

        return steps.reduce { a, b -> Util().lcm(a, b) }
    }

    private fun setMoonsToInitialState() = initialMoonsState.map { Moon(it.gravity.copy()) }.toList()

    private fun forEachPair(action: (Int, Int) -> Unit) {
        for (j in moons.indices) {
            for (k in j + 1 until moons.size) {
                action(j, k)
            }
        }
    }

    fun calculateTotalEnergyInTheSystem(): Int = moons.map { it.calculateTotalEnergy() }.sum()
}

data class Moon(val gravity: Gravity, private val velocity: Velocity = Velocity(0, 0, 0)) {

    fun applyVelocity(moon: Moon) {
        this.velocity.x -= this.gravity.x.compareTo(moon.gravity.x)
        this.velocity.y -= this.gravity.y.compareTo(moon.gravity.y)
        this.velocity.z -= this.gravity.z.compareTo(moon.gravity.z)
    }

    fun updateGravity() {
        this.gravity.x += this.velocity.x
        this.gravity.y += this.velocity.y
        this.gravity.z += this.velocity.z
    }

    fun calculateTotalEnergy(): Int = calculateKineticEnergy() * calculatePotentialEnergy()

    private fun calculatePotentialEnergy(): Int = abs(gravity.x) + abs(gravity.y) + abs(gravity.z)

    private fun calculateKineticEnergy(): Int = abs(velocity.x) + abs(velocity.y) + abs(velocity.z)

    override fun toString(): String {
        return "Moon(pos=$gravity, vel=$velocity)"
    }
}

typealias Velocity = Gravity

data class Gravity(var x: Int, var y: Int, var z: Int) {
    override fun toString(): String {
        return "<x=$x, y=$y, z=$z>"
    }
}