package day2

/**
 * @author <ahref="mailto:226154@student.pwr.edu.pl "        >        Hanna Grodzicka</a>
 */
class PartOne {

    static def OPCODES = [
            1: { int x, int y -> x + y },
            2: { int x, int y -> x * y }
    ]

    static void main(String[] args) {
        def input = [1, 12, 2, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 13, 1, 19, 1, 19, 9, 23, 1, 5, 23, 27, 1, 27, 9, 31, 1, 6, 31, 35, 2, 35, 9, 39, 1, 39, 6, 43, 2, 9, 43, 47, 1, 47, 6, 51, 2, 51, 9, 55, 1, 5, 55, 59, 2, 59, 6, 63, 1, 9, 63, 67, 1, 67, 10, 71, 1, 71, 13, 75, 2, 13, 75, 79, 1, 6, 79, 83, 2, 9, 83, 87, 1, 87, 6, 91, 2, 10, 91, 95, 2, 13, 95, 99, 1, 9, 99, 103, 1, 5, 103, 107, 2, 9, 107, 111, 1, 111, 5, 115, 1, 115, 5, 119, 1, 10, 119, 123, 1, 13, 123, 127, 1, 2, 127, 131, 1, 131, 13, 0, 99, 2, 14, 0, 0]
        def resultArray = getArray(input)
        println(resultArray[0]) // 3409710
    }

    static def getArray(List<Integer> array) {
        0.step array.size(), 4, {
            if (array[it] == 99) return false
            def closure = OPCODES.get(array[it])
            if (closure == null) return
            def position1 = array[it + 1]
            def position2 = array[it + 2]
            def result = closure(array[position1], array[position2])
            def position3 = array[it + 3]
            array[position3] = result
        }
        return array
    }
}
