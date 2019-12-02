package day1;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PartTwo {

    public static void main(String[] args) throws IOException {
        String input = "day1/part_two.txt";
        RocketEquationDoubleChecker rocketEquationDoubleChecker = new RocketEquationDoubleChecker(input);
        int result = rocketEquationDoubleChecker.getTotalFuelRequirement();
        System.out.println(result); // 4770725
    }

    static class RocketEquationDoubleChecker {

        private final List<Integer> massOfEachModule;

        public RocketEquationDoubleChecker(String input) throws IOException {
            this.massOfEachModule = Util.readInputFile(input);
        }

        static int getFuelForModule(int mass) {
            int totalRequirement = 0;
            int requirement = mass;
            while ((requirement = Math.floorDiv(requirement, 3) - 2) > 0) {
                totalRequirement += requirement;
            }
            return totalRequirement;
        }

        public int getTotalFuelRequirement() {
            return massOfEachModule.stream()
                    .mapToInt(RocketEquationDoubleChecker::getFuelForModule)
                    .sum();
        }
    }
}
