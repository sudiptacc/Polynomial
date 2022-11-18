package polynomial;

import java.util.ArrayList;

/* This class is only for class/static methods for math formulas and other utilities */
public class MathUtil {

    public static final double EPSILON = 1e-9;

    /** 
     * Method to check if two double values are close to each other (almost equal)
     * @param value the value that is being checked
     * @param reference the number that value is being checked against or compared to
     * @param tolerance the allowed distance or margin of error between value and reference
     * @return whether or not the value and reference are close, according to the tolerance
     */
    public static boolean isClose(double value, double reference, double tolerance) {
        return Math.abs(value - reference) < tolerance;
    }

    /**
     * This method is to condense the code to add two ArrayLists together into a single method.
     * @param firstTerms the first ArrayList of Monomials
     * @param secondTerms the second ArrayList of Monomials
     * @return the ArrayList of the addition of the two other ArrayLists
     */
    public static ArrayList<Term> sumOfTermLists(ArrayList<Term> firstTerms, ArrayList<Term> secondTerms) {
        ArrayList<Term> combinedTerms = new ArrayList<Term>();
        combinedTerms.addAll(firstTerms);
        combinedTerms.addAll(secondTerms);
        return combinedTerms;
    }
}
