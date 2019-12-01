package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Util {

    private Util() {
        throw new IllegalStateException("Utility class");
    }

    static List<Integer> readInputFile(String input) throws IOException {
        ClassLoader classLoader = Util.class.getClassLoader();
        String filePath = classLoader.getResource(input).getFile();
        return Files.readAllLines(Paths.get(filePath)).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
