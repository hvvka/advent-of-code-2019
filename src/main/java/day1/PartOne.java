package day1;

import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 * <p>
 * At the first Go / No Go poll, every Elf is Go until the Fuel Counter-Upper.
 * They haven't determined the amount of fuel required yet.
 * Fuel required to launch a given module is based on its mass.
 * Specifically, to find the fuel required for a module,
 * take its mass, divide by three, round down, and subtract 2.
 * <p>
 * For example:
 * For a mass of 12, divide by 3 and round down to get 4, then subtract 2 to get 2.
 * For a mass of 14, dividing by 3 and rounding down still yields 4, so the fuel required is also 2.
 * For a mass of 1969, the fuel required is 654.
 * For a mass of 100756, the fuel required is 33583.
 * The Fuel Counter-Upper needs to know the total fuel requirement.
 * To find it, individually calculate the fuel needed for the mass of each module (your puzzle input),
 * then add together all the fuel values.
 * <p>
 * What is the sum of the fuel requirements for all of the modules on your spacecraft?
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
            this.massOfEachModule = Util.readInputFile(input);
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
