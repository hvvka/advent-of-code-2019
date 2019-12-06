package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Util {

    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String> readInputFile(String input) throws IOException {
        ClassLoader classLoader = Util.class.getClassLoader();
        String filePath = classLoader.getResource(input).getFile();
        return Files.readAllLines(Paths.get(filePath));
    }

    public static List<Integer> readFileAsIntegers(String input) throws IOException {
        return readInputFile(input).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static <T, E> T getKeyByValue(Map<T, List<E>> map, E value) {
        return map.entrySet().stream()
                .filter(entry -> entry.getValue().contains(value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList()).get(0);
    }

    public static <T> List<T> intersect(List<T> a, List<T> b) {
        return a.stream()
                .distinct()
                .filter(x -> b.stream().anyMatch(y -> y == x))
                .collect(Collectors.toList());
    }
}
