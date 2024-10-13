package sengen24.model.generation.util;

import java.util.Random;

/**
 * Helper Singleton class to easily get new random numbers or pick random objects from object lists.
 */
public class RandomNumberGenerator {
    private Random random;
    private static RandomNumberGenerator randomNumberGenerator;

    private RandomNumberGenerator() {
        this.random = new Random();
    };

    /**
     * Returns the only instance of the random number generator.
     * @return An instance of the random number generator.
     */
    public static RandomNumberGenerator getInstance() {
        if(randomNumberGenerator == null) {
            randomNumberGenerator = new RandomNumberGenerator();
        }
        return randomNumberGenerator;
    }

    /**
     * Returns a random int value.
     * @param range The range to select the value (max amount).
     * @return A random int value within the range.
     */
    public int makeRandomInt(int range) {
        return random.nextInt(range);
    }

    /**
     * Returns a random double value.
     * @return A random double value.
     */
    public double makeRandomDouble() {
        return random.nextDouble();
    }

    /**
     * Returns a random boolean value.
     * @return A random boolean value.
     */
    public boolean makeRandomBoolean() {
        return random.nextBoolean();
    }


    /**
     * Picks a random object from the list of objects.
     * @param objects A list of objects.
     * @return A random element from this list.
     */
    public Object pickRandomObject(Object[] objects) {
        int random = makeRandomInt(objects.length);
        return objects[random];
    }

}
