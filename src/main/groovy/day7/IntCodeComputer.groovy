package day7

import static day5.PartOne.getCodeArray
import static day5.PartOne.getParameterValue

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class IntCodeComputer {

    private List<Integer> array

    private List<Integer> inputs

    boolean isTerminated

    IntCodeComputer(List<Integer> array, List<Integer> inputs) {
        this.array = array
        this.inputs = inputs
        this.isTerminated = false
    }

    static void main(String[] args) {
        def input = [3, 225, 1, 225, 6, 6, 1100, 1, 238, 225, 104, 0, 1101, 11, 91, 225, 1002, 121, 77, 224, 101, -6314, 224, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 3, 224, 1, 223, 224, 223, 1102, 74, 62, 225, 1102, 82, 7, 224, 1001, 224, -574, 224, 4, 224, 102, 8, 223, 223, 1001, 224, 3, 224, 1, 224, 223, 223, 1101, 28, 67, 225, 1102, 42, 15, 225, 2, 196, 96, 224, 101, -4446, 224, 224, 4, 224, 102, 8, 223, 223, 101, 6, 224, 224, 1, 223, 224, 223, 1101, 86, 57, 225, 1, 148, 69, 224, 1001, 224, -77, 224, 4, 224, 102, 8, 223, 223, 1001, 224, 2, 224, 1, 223, 224, 223, 1101, 82, 83, 225, 101, 87, 14, 224, 1001, 224, -178, 224, 4, 224, 1002, 223, 8, 223, 101, 7, 224, 224, 1, 223, 224, 223, 1101, 38, 35, 225, 102, 31, 65, 224, 1001, 224, -868, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 5, 224, 1, 223, 224, 223, 1101, 57, 27, 224, 1001, 224, -84, 224, 4, 224, 102, 8, 223, 223, 1001, 224, 7, 224, 1, 223, 224, 223, 1101, 61, 78, 225, 1001, 40, 27, 224, 101, -89, 224, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 1, 224, 1, 224, 223, 223, 4, 223, 99, 0, 0, 0, 677, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1105, 0, 99999, 1105, 227, 247, 1105, 1, 99999, 1005, 227, 99999, 1005, 0, 256, 1105, 1, 99999, 1106, 227, 99999, 1106, 0, 265, 1105, 1, 99999, 1006, 0, 99999, 1006, 227, 274, 1105, 1, 99999, 1105, 1, 280, 1105, 1, 99999, 1, 225, 225, 225, 1101, 294, 0, 0, 105, 1, 0, 1105, 1, 99999, 1106, 0, 300, 1105, 1, 99999, 1, 225, 225, 225, 1101, 314, 0, 0, 106, 0, 0, 1105, 1, 99999, 1008, 677, 226, 224, 1002, 223, 2, 223, 1006, 224, 329, 101, 1, 223, 223, 8, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 344, 101, 1, 223, 223, 1107, 226, 677, 224, 102, 2, 223, 223, 1006, 224, 359, 101, 1, 223, 223, 1007, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 374, 101, 1, 223, 223, 7, 677, 677, 224, 102, 2, 223, 223, 1005, 224, 389, 1001, 223, 1, 223, 108, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 404, 101, 1, 223, 223, 1008, 226, 226, 224, 102, 2, 223, 223, 1005, 224, 419, 1001, 223, 1, 223, 1107, 677, 226, 224, 102, 2, 223, 223, 1005, 224, 434, 1001, 223, 1, 223, 1108, 677, 677, 224, 102, 2, 223, 223, 1006, 224, 449, 1001, 223, 1, 223, 7, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 464, 101, 1, 223, 223, 1008, 677, 677, 224, 102, 2, 223, 223, 1005, 224, 479, 101, 1, 223, 223, 1007, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 494, 101, 1, 223, 223, 8, 677, 226, 224, 1002, 223, 2, 223, 1005, 224, 509, 101, 1, 223, 223, 1007, 677, 677, 224, 1002, 223, 2, 223, 1006, 224, 524, 101, 1, 223, 223, 107, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 539, 101, 1, 223, 223, 107, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 554, 1001, 223, 1, 223, 7, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 569, 1001, 223, 1, 223, 107, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 584, 101, 1, 223, 223, 1107, 677, 677, 224, 102, 2, 223, 223, 1005, 224, 599, 101, 1, 223, 223, 1108, 226, 677, 224, 102, 2, 223, 223, 1006, 224, 614, 101, 1, 223, 223, 8, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 629, 101, 1, 223, 223, 108, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 644, 1001, 223, 1, 223, 108, 226, 226, 224, 102, 2, 223, 223, 1005, 224, 659, 101, 1, 223, 223, 1108, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 674, 1001, 223, 1, 223, 4, 223, 99, 226]
        def intCodeComputer = new IntCodeComputer(input, [])
        def result = intCodeComputer.getOutput(5)
        println(result) // 15724522
    }

    private static def OPCODES = [
            1: [4, { int x, int y -> x + y }],
            2: [4, { int x, int y -> x * y }],
            3: [2, { -> }],
            4: [2, { int x -> println(x) }],
            5: [3, { int x, int y -> if (x != 0) y else 0 }],
            6: [3, { int x, int y -> if (x == 0) y else 0 }],
            7: [4, { int x, int y -> if (x < y) 1 else 0 }],
            8: [4, { int x, int y -> if (x == y) 1 else 0 }]
    ]

    def getOutput(int input) {
        this.inputs += input
        int pointer = 0
        def output = null
        while (pointer < array.size()) {
            def codeArray = getCodeArray(array[pointer])
            if (codeArray[0] == 99) break
            def operation = OPCODES.get(codeArray[0])
            def closure = operation[1]
            if (closure == null) continue

            if (codeArray[0] == 1 || codeArray[0] == 2 || codeArray[0] == 7 || codeArray[0] == 8) {
                int value1 = getParameterValue(array, pointer + 1, codeArray[1])
                int value2 = getParameterValue(array, pointer + 2, codeArray[2])
                int result = closure(value1, value2)
                int value3 = array[pointer + 3]
                array[value3] = result
                pointer += operation[0]
            } else if (codeArray[0] == 5 || codeArray[0] == 6) {
                int value1 = getParameterValue(array, pointer + 1, codeArray[1])
                int value2 = getParameterValue(array, pointer + 2, codeArray[2])
                def result = closure(value1, value2)
                if (result == 0) pointer += operation[0]
                else pointer = result
            } else if (codeArray[0] == 3) {
                println(inputs)
                int value1 = array[pointer + 1]
                array[value1] = inputs.remove(0)
                pointer += operation[0]
            } else if (codeArray[0] == 4) {
                output = getParameterValue(array, pointer + 1, codeArray[1])
                pointer += operation[0]
            }
        }
        this.isTerminated = true
        return output
    }
}
