package day13

import Util
import day9.IntcodeComputer

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val input = Util().readFile("day13/input.txt")

    val arcadeCabinet = ArcadeCabinet(input)
    val result = arcadeCabinet.countBlockTiles()
    println(result) // 265
}

internal class ArcadeCabinet(private val instructions: String) {

    private val intcodeComputer = IntcodeComputer(this.instructions)

    private val tiles = mutableListOf<Tile>()

    init {
        intcodeComputer.execute()
        val output = intcodeComputer.getOutput()
        (0..output.size - 4 step 3).mapTo(tiles) {
            Tile(
                output[it],
                output[it + 1],
                TileId.values()[output[it + 2].toInt()]
            )
        }
    }

    fun countBlockTiles(): Int = tiles.filter { tile -> tile.tileId == TileId.BLOCK }.count()
}

enum class TileId {
    EMPTY, WALL, BLOCK, HORIZONTAL, BALL
}

data class Tile(val x: Long, val y: Long, val tileId: TileId) {
    override fun toString(): String {
        return "Tile(x=$x, y=$y, tileId=$tileId)"
    }
}
