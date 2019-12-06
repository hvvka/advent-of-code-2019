package day6;

import day1.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PartOne {

    public static void main(String[] args) throws IOException {
        String input = "day6/part_one.txt";
        OrbitCountChecksums orbitCountChecksums = new OrbitCountChecksums(input);
        int result = orbitCountChecksums.getChecksum();
        System.out.println(result); // 295834
    }

    static class OrbitCountChecksums {

        private static final String CENTER_OF_MASS = "COM";

        private static int XD = 0;

        // TODO: provide data structure
        private final Map<String, List<String>> orbits;


        public OrbitCountChecksums(String input) throws IOException {
            this.orbits = new HashMap<>();
            this.orbits.put(CENTER_OF_MASS, new ArrayList<>());
            this.parseInputFile(input);
        }

        private void parseInputFile(String input) throws IOException {
            Util.readInputFile(input).stream()
                    .map(line -> line.split("\\)"))
                    .forEach(pair -> {
                                if (!this.orbits.containsKey(pair[0])) {
                                    List<String> children = new ArrayList<>();
                                    children.add(pair[1]);
                                    this.orbits.put(pair[0], children); // is mutable list?
                                } else {
                                    this.orbits.get(pair[0]).add(pair[1]);
                                }
                            }
                    );
        }

        public int getChecksum() {
            this.orbits.get(CENTER_OF_MASS).forEach(this::getDistanceFromRoot);
            return XD;
        }

        private Integer getDistanceFromRoot(String child) {
            int counter = 1;
            if (this.orbits.containsKey(child)) {
                // go deeper
                counter += this.orbits.get(child).stream()
                        .map(this::getDistanceFromRoot)
                        .reduce(0, Integer::sum); // TODO: remove code duplication
            }
            // else it's a leaf
            XD += counter; // sublime workaroud
            return counter;
        }
    }
}
