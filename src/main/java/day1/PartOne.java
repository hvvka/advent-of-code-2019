package day1;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class PartOne {

    public static void main(String[] args) throws IOException {
        String input = "day1/part_one.txt";
        FuelCounterUpper fuelCounterUpper = new FuelCounterUpper(input);
        int result = fuelCounterUpper.getTotalFuelRequirement();
        System.out.println(result); // 3182375
    }

    static class FuelCounterUpper {

        private final List<Integer> massOfEachModule;

        public FuelCounterUpper(String input) throws IOException {
            this.massOfEachModule = Util.readFileAsIntegers(input);
        }

        static int getFuelForModule(int mass) {
            return Math.floorDiv(mass, 3) - 2;
        }

        public int getTotalFuelRequirement() {
            return this.massOfEachModule.stream()
                    .mapToInt(FuelCounterUpper::getFuelForModule)
                    .sum();
        }
    }
}
