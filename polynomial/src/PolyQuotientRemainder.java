package polynomial.src;

/**
 * Simple key-value pair class to represent polynomial division quotient and remainder 
 */
public class PolyQuotientRemainder {

    private Polynomial quotient;
    private Polynomial remainder;

    PolyQuotientRemainder(Polynomial polyQuotient, Polynomial polyRemainder) {
        quotient = polyQuotient;
        remainder = polyRemainder;
    }

    public Polynomial getQuotient() {
        return quotient;
    }

    public Polynomial getRemainder() {
        return remainder;
    }

    @Override
    public String toString() {
        return "Q: " + quotient + " R: " + remainder;
    }
}
