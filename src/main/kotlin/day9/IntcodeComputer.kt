package day9

import java.util.*


/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
open class IntcodeComputer(instructions: String, vararg input: Int) {

    private var memory: Memory
    private var instructionPointer = 0
    private var relativeBaseOffset = 0
    private var computerMode = Mode.NORMAL
    private var inputDigits: List<Long> = input.map { it.toLong() }
    private var inputIndex = 0
    private val output: MutableList<Long> = ArrayList()
    var isTerminated = false
        private set

    init {
        val parsedInstructions = instructions.split(",").map { it.toLong() }
        memory = Memory(parsedInstructions)
    }

    fun getRegisterValue(index: Int): Long {
        return memory[index]
    }

    fun addInput(vararg input: Int) {
        this.inputDigits += input.map { it.toLong() }
    }

    fun getOutput(): List<Long> {
        return output
    }

    val lastOutput: Long
        get() = output[output.size - 1]

    fun waitAfterOutputMode() {
        computerMode = Mode.WAIT_AFTER_OUTPUT
    }

    fun execute() {
        while (!isTerminated) {
            val opcode = memory[instructionPointer]
            if (opcode == 99L) {
                isTerminated = true
                return
            }
            val instruction = (opcode % 100).toInt()
            val firstMode = opcode / 100 % 10
            val secondMode = opcode / 1000 % 10
            val thirdMode = opcode / 10000 % 10
            when (instruction) {
                1 -> add(firstMode, secondMode, thirdMode)
                2 -> multiply(firstMode, secondMode, thirdMode)
                3 -> handleInput(firstMode)
                4 -> {
                    handleOutput(firstMode)
                    if (computerMode == Mode.WAIT_AFTER_OUTPUT) {
                        return
                    }
                }
                5 -> jumpIfNotZero(firstMode, secondMode)
                6 -> jumpIfZero(firstMode, secondMode)
                7 -> lessThan(firstMode, secondMode, thirdMode)
                8 -> equals(firstMode, secondMode, thirdMode)
                9 -> adjustRelativeBase(firstMode)
                else -> throw IllegalArgumentException("Unknown instruction: $instruction")
            }
        }
    }

    private fun add(firstMode: Long, secondMode: Long, thirdMode: Long) {
        val firstValue = getInputValue(1, firstMode)
        val secondValue = getInputValue(2, secondMode)
        val writeIndex = getOutputValue(3, thirdMode)
        memory.put(writeIndex, firstValue + secondValue)
        instructionPointer += 4
    }

    private fun multiply(firstMode: Long, secondMode: Long, thirdMode: Long) {
        val firstValue = getInputValue(1, firstMode)
        val secondValue = getInputValue(2, secondMode)
        val writeIndex = getOutputValue(3, thirdMode)
        memory.put(writeIndex, firstValue * secondValue)
        instructionPointer += 4
    }

    private fun handleInput(firstMode: Long) {
        val outputAddress = getOutputValue(1, firstMode)
        val inputValue = inputDigits[inputIndex++]
        memory.put(outputAddress, inputValue)
        instructionPointer += 2
    }

    private fun handleOutput(firstMode: Long) {
        val outputValue = getInputValue(1, firstMode)
        output += outputValue
        instructionPointer += 2
    }

    private fun jumpIfNotZero(firstMode: Long, secondMode: Long) {
        val firstValue = getInputValue(1, firstMode)
        val secondValue = getInputValue(2, secondMode)
        when {
            firstValue != 0L -> instructionPointer = secondValue.toInt()
            else -> instructionPointer += 3
        }
    }

    private fun jumpIfZero(firstMode: Long, secondMode: Long) {
        val firstValue = getInputValue(1, firstMode)
        val secondValue = getInputValue(2, secondMode)
        when (firstValue) {
            0L -> instructionPointer = secondValue.toInt()
            else -> instructionPointer += 3
        }
    }

    private fun lessThan(firstMode: Long, secondMode: Long, thirdMode: Long) {
        val firstValue = getInputValue(1, firstMode)
        val secondValue = getInputValue(2, secondMode)
        val writeIndex = getOutputValue(3, thirdMode)
        when {
            firstValue < secondValue -> memory.put(writeIndex, 1L)
            else -> memory.put(writeIndex, 0L)
        }
        instructionPointer += 4
    }

    private fun equals(firstMode: Long, secondMode: Long, thirdMode: Long) {
        val firstValue = getInputValue(1, firstMode)
        val secondValue = getInputValue(2, secondMode)
        val writeIndex = getOutputValue(3, thirdMode)
        when (firstValue) {
            secondValue -> memory.put(writeIndex, 1L)
            else -> memory.put(writeIndex, 0L)
        }
        instructionPointer += 4
    }

    private fun adjustRelativeBase(firstMode: Long) {
        val outputValue = getInputValue(1, firstMode)
        relativeBaseOffset += outputValue.toInt()
        instructionPointer += 2
    }

    private fun getInputValue(offset: Int, mode: Long): Long {
        val argument = memory[instructionPointer + offset]
        return when (mode) {
            0L -> memory[argument]
            1L -> argument
            else -> memory[relativeBaseOffset + argument]
        }
    }

    private fun getOutputValue(offset: Int, mode: Long): Long {
        val argument = memory[instructionPointer + offset]
        return when (mode) {
            0L -> argument
            1L -> argument
            else -> relativeBaseOffset + argument
        }
    }

    private enum class Mode {
        NORMAL, WAIT_AFTER_OUTPUT
    }

    private class Memory internal constructor(instructions: List<Long>) {

        private val program: MutableMap<Int, Long>

        fun put(index: Long, value: Long) {
            program[index.toInt()] = value
        }

        operator fun get(index: Int): Long {
            require(index >= 0) { "Negative index" }
            return program.getOrDefault(index, 0L)
        }

        operator fun get(index: Long): Long {
            require(index >= 0) { "Negative index" }
            return program.getOrDefault(index.toInt(), 0L)
        }

        init {
            program = HashMap(instructions.size)
            for (i in instructions.indices) {
                program[i] = instructions[i]
            }
        }
    }
}