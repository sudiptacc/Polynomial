package polynomial.src;

/* This class is only for class/static methods for math formulas and other utilities */
public class MathUtil {

    public static final double ROUNDING_THRESHOLD = 1e-5;
    public static final double EPSILON = 1e-10;
    public static final int MAX_ITER = 1000;

    /** 
     * Method to check if two double values are close to each other (almost equal).
     * @param value the number that is being checked
     * @param reference the number that value is being checked against or compared to
     * @param tolerance the allowed distance or margin of error between value and reference
     * @return whether or not the value and reference are close, according to the tolerance
     */
    public static boolean isClose(double value, double reference, double tolerance) {
        return Math.abs(value - reference) < tolerance;
    }

    /**
     * Round a double value to an int value.
     * @param value the number that is being rounded
     * @return the number rounded, as an int
     */
    public static int roundToInt(double value) {
        if (value < 0) return (int) (value - 0.5);
        else return (int) (value + 0.5);

    }
}
