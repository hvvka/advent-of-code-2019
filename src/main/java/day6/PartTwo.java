package day6;

import day1.Util;

import java.io.IOException;
import java.util.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PartTwo {

    public static void main(String[] args) throws IOException {
        String input = "day6/part_one.txt";
        OrbitalTransfers orbitalTransfers = new OrbitalTransfers(input);
        int result = orbitalTransfers.getDistanceFromMeToSanta();
        System.out.println(result); // 361
    }

    static class OrbitalTransfers {

        private static final String CENTER_OF_MASS = "COM";
        private static final String YOU = "YOU";
        private static final String SANTA = "SAN";
        private final Map<String, List<String>> orbits;

        public OrbitalTransfers(String input) throws IOException {
            this.orbits = new HashMap<>();
            this.orbits.put(CENTER_OF_MASS, new ArrayList<>());
            this.parseInputFile(input);
        }

        private void parseInputFile(String input) throws IOException {
            Util.readInputFile(input).stream()
                    .map(line -> line.split("\\)"))
                    .forEach(this::addToMap);
        }

        private void addToMap(String[] pair) {
            if (!this.orbits.containsKey(pair[0])) {
                this.orbits.put(pair[0], new ArrayList<>(Collections.singletonList(pair[1])));
            } else {
                this.orbits.get(pair[0]).add(pair[1]);
            }
        }

        public int getDistanceFromMeToSanta() {
            List<String> pathToSanta = getPathTo(SANTA);
            List<String> pathToMe = getPathTo(YOU);
            List<String> commonPath = Util.intersect(pathToSanta, pathToMe);
            pathToSanta.removeAll(commonPath);
            pathToMe.removeAll(commonPath);
            return pathToMe.size() + pathToSanta.size();
        }

        private List<String> getPathTo(String node) {
            String parent = Util.getKeyByValue(this.orbits, node);
            List<String> path = new ArrayList<>();
            while (!parent.equals(CENTER_OF_MASS)) {
                path.add(parent);
                parent = Util.getKeyByValue(this.orbits, parent);
            }
            return path;
        }
    }
}
