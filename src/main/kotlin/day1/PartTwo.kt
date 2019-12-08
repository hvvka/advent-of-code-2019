package day1

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val image = getImageLayers()
    val decodedImage = mutableListOf<Char>()

    for (i in image[0].indices) {
        for (layer in image) {
            if (layer[i] == '0' || layer[i] == '1') {
                decodedImage.add(layer[i])
                break
            }
        }
    }

    for (i in 0 until decodedImage.size) {
        if (i % imageWidth == 0) println()
        print(decodedImage[i])
    }
}
