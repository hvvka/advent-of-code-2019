package day1

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */

fun main() {
    val image = getImageLayers()
    val decodedImage = arrayOfNulls<Char>(imageSize)

    for (layer in image) {
        for ((index, pixel) in layer.withIndex()) {
            when {
                pixel != '2' && decodedImage[index] == null -> decodedImage[index] = pixel
            }
        }
    }

    for (i in decodedImage.indices) {
        if (i % imageWidth == 0) println()
        print(decodedImage[i])
    }
    // HZCZU
}
