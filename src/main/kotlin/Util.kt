/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class Util {

    fun readFile(filename: String): String = this::class.java.getResource(filename).readText()
}