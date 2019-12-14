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

    private var score = 0

    private val intcodeComputer = IntcodeComputer(this.instructions)

    private var tiles = mutableListOf<Tile>()

    private fun mapOutputToTiles() {
        val output = intcodeComputer.getOutput()
        for (i in output.indices step 3) {
            val tileValue = output[i + 2].toInt()
            if (tileValue >= TileId.values().size) {
                score = tileValue
                continue
            }
            tiles.add(
                Tile(
                    output[i],
                    output[i + 1],
                    TileId.values()[output[i + 2].toInt()]
                )
            )
        }
    }

    fun playForFree(): Int {
        intcodeComputer.waitForInputMode()
        while (!intcodeComputer.isTerminated) {
            intcodeComputer.execute()
            mapOutputToTiles()
            val input = getComputerInput()
            intcodeComputer.addInput(input)
        }
        mapOutputToTiles()
        return score
    }

    private fun getComputerInput(): Int {
        val paddle: Tile = tiles.last { (_, _, tile) -> tile == TileId.HORIZONTAL }
        val ball: Tile = tiles.last { (_, _, tile) -> tile == TileId.BALL }
        return when {
            ball.x < paddle.x -> -1
            ball.x > paddle.x -> 1
            else -> 0
        }
    }

    fun getTiles(): List<Tile> = tiles

    private fun printArcadeMap() {
        val sizeX = tiles.maxBy { (x, _) -> x }!!.x + 1 // TODO: !!
        val sizeY = tiles.maxBy { (_, y) -> y }!!.y + 1
        val arcadeMap = Array(sizeY.toInt()) { CharArray(sizeX.toInt()) }
        tiles.retainAll { (x, y, _) -> !(x == -1L && y == 0L) }
        for ((x, y, tile) in tiles) arcadeMap[y.toInt()][x.toInt()] = tile.ascii
        arcadeMap.forEach {
            it.forEach { print(it) }
            println()
        }
    }

    fun countBlockTiles(): Int {
        intcodeComputer.execute()
        mapOutputToTiles()
        return tiles.filter { tile -> tile.tileId == TileId.BLOCK }.count()
    }
}

enum class TileId(val ascii: Char) {
    EMPTY(' '), WALL('█'), BLOCK('X'), HORIZONTAL('—'), BALL('O')
}

data class Tile(val x: Long, val y: Long, val tileId: TileId) {
    override fun toString(): String {
        return "Tile(x=$x, y=$y, tileId=$tileId)"
    }
}
