package polynomial.src;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A Java based object model of polynomials and their properties. This class
 * builds upon the Term class, in which a Term is described as a component of
 * Polynomial. A polynomial can be constructed using term(s), an ArrayList of
 * term(s), or the real roots and leading coefficient of the polynomial.
 */
public class Polynomial {

    private ArrayList<Term> terms = new ArrayList<Term>();
    private int degree;
    private double leadingCoeff;
    private Term leadingTerm;

    /** The construction of a polynomial as a combination of Terms */
    public Polynomial(Term ... polyTerms) {
        if (polyTerms.length >= 1) {
            for (Term term : polyTerms) {
                terms.add(term);
            }
        } 
        else {
            throw new IllegalArgumentException("A polynomial must have at least 1 term.");
        }
        updatePolynomial();
    }

    /** The construction of a polynomial using an array of Terms */
    public Polynomial(ArrayList<Term> polyTerms) {
        if (polyTerms.size() >= 1) {
            for (Term term : polyTerms) {
                terms.add(term);
            }
        } 
        else {
            throw new IllegalArgumentException("A polynomial must have at least 1 term.");
        }
        updatePolynomial();
    }

    public Polynomial(double coefficient, double... roots) {
        /** Initialize the output with the coefficient as the first factor */
        Polynomial output = new Polynomial(new Term(coefficient, 0));
        for (double root : roots) {
            /** For each root, multiply the polynomial by the factor form of the root */
            output = output.multiply(new Polynomial(new Term(1, 1), new Term(-root, 0)));
        }
        terms = output.terms();
        updatePolynomial();
    }

    public int getDegree() {
        return degree;
    }

    public double getLeadingCoeff() {
        return leadingCoeff;
    }

    public Term getLeadingTerm() {
        return leadingTerm;
    }

    public ArrayList<Term> terms() {
        return new ArrayList<Term>(terms);
    }

    public Polynomial clone() {
        return new Polynomial(terms);
    }

    public Term getTerm(int index) {
        return terms.get(index);
    }

    public void addTerm(Term Term) {
        terms.add(Term);
        updatePolynomial();
    }

    public void removeTerm(int index) {
        terms.remove(index);
        updatePolynomial();
    }

    @Override
    public String toString() {
        ArrayList<String> stringRepresentations = new ArrayList<String>();
        for (Term Term : terms) {
            stringRepresentations.add(Term.toString());
        }
        return String.join( " + ", stringRepresentations);
    }

    /* Update method, to be run upon initialization and when terms list is modified */
    private void updatePolynomial() {
        toStandardForm();
        combineLikeTerms();
        removeDuplicateZeroTerms();
        /**
         * To account for any operations that may add or remove a term, thereby possibly affecting the leading term
         * and also the degree and leading coefficient of the polynomial. 
         */
        leadingTerm = terms.get(0);
        degree = leadingTerm.getDegree();
        leadingCoeff = leadingTerm.getCoefficient();
    }

    /* Sort the list of terms by their degree */
    private void toStandardForm() {
        terms.sort(new SortByDegree());
    }

    private ArrayList<Term> sumOfTermLists(ArrayList<Term> firstTerms, ArrayList<Term> secondTerms) {
        ArrayList<Term> combinedTerms = new ArrayList<Term>();
        combinedTerms.addAll(firstTerms);
        combinedTerms.addAll(secondTerms);
        return combinedTerms;
    }

    /* Removes any terms that have a coefficient of 0. Those terms don't contribute anything meaningful */
    private void removeDuplicateZeroTerms() {
        ArrayList<Term> redundantTerms = new ArrayList<Term>();
        Term zero = new Term(0, 0);

        for (Term term : terms) {
            if (term.getCoefficient() == 0) redundantTerms.add(term);
        }
        terms.removeAll(redundantTerms);
        
        /* Add a zero term to indicate that the polynomial is 0 if there are no other terms */
        if (terms.isEmpty()) terms.add(zero);
    }

    private void combineLikeTerms() {
        /* No need to invoke toStandardForm() here, because updatePolynomial() calls it before this method. */
        ArrayList<Term> combinedLikeTerms = new ArrayList<Term>();
        /* Create a copy of the terms,  */
        ArrayList<Term> termsCopy = new ArrayList<Term>(terms);

        /* The idea here is to repeatedly check for like terms, and remove terms that have already been checked. */
        while (!termsCopy.isEmpty()) {
            Term firstTerm = termsCopy.get(0); 
            Term sumOfLikeTerms = termsCopy.get(0); /* sum is different from firstTerm, as terms may be added to it. */

            /* We check the term AFTER the current term, to prevent duplication and terms getting doubled. */
            for (int i = terms.indexOf(firstTerm) + 1; i < terms.size(); i++) {
                /* If they are like terms (that is, they have the same degree), then combine the like terms.  */
                if (firstTerm.getDegree() == terms.get(i).getDegree()) {
                    sumOfLikeTerms = sumOfLikeTerms.add(terms.get(i));
                    /* Remove the other like term from the termsCopy list so there is no duplication. */
                    termsCopy.remove(terms.get(i));
                } 
            }
            /* Remove the first term, so that terms that have not been checked can proceed. */
            termsCopy.remove(0);
            combinedLikeTerms.add(sumOfLikeTerms);
        }

        /* Replace the current list of terms with the updated list of terms. */
        terms = combinedLikeTerms;
    }

    /**
     * Produces the evaluation of the Polynomial at a certain x value.
     * @param a the value at which the Polynomial should be evaluated
     * @return the value of the Polynomial at x = a
     */
    public double valueAt(double a) {
        double value = 0;
        for (Term term : terms) {
            value += term.valueAt(a);
        }
        return value;
    }
    
    /**
     * Produces the general derivative of the Polynomial.
     * @return the Polynomial that is the general derivative of the Polynomial
     * @see Term.derivative
     */
    public Polynomial derivative() {
        ArrayList<Term> derivativeTerms = new ArrayList<Term>();
        for (Term term : terms) {
            derivativeTerms.add(term.derivative());
        }
        return new Polynomial(derivativeTerms);
    }

    /** 
     * Produces the negation of the Polynomial (all the terms are just negated).
     * @return a Polynomial that represents the negation of this Polynomial
     */
    public Polynomial negation() {
        ArrayList<Term> negatedTerms = new ArrayList<Term>();
        for (Term term : terms) {
            negatedTerms.add(term.negation());
        }
        return new Polynomial(negatedTerms);
    }

    /** 
     * Produces the sum of two Polynomials.
     * @param addend the Polynomial that is being added
     * @return the sum of the Polynomials
     * @see sumOfTermLists 
     * @see combineLikeTerms
     */
    public Polynomial add(Polynomial addend) {        
        return new Polynomial(sumOfTermLists( terms, addend.terms() ));
    }

    /** 
     * Produces the difference of two Polynomials.
     * @param subtrahend the Polynomial that is the subtrahend
     * @return the difference of the Polynomials
     * @see sumOfTermLists 
     * @see combineLikeTerms
     */
    public Polynomial subtract(Polynomial subtrahend) {
        return new Polynomial(sumOfTermLists(terms, subtrahend.negation().terms()));
    }

    /** 
     * Produces the product of two Polynomials.
     * @param multiplicand the other factor
     * @return the product of the two Polynomials
     */
    public Polynomial multiply(Polynomial multiplicand) {
        ArrayList<Term> multiplierTerms = terms;
        ArrayList<Term> multiplicandTerms = multiplicand.terms();
        ArrayList<Term> productPolynomialTerms = new ArrayList<Term>();

        for (Term thisTerm : multiplierTerms) {
            for (Term multiplicandTerm : multiplicandTerms) {
                productPolynomialTerms.add(thisTerm.multiply(multiplicandTerm));
            }
        }
        return new Polynomial(productPolynomialTerms);
    }

    /** 
     * Produces the quotient and remainder of the division of two Polynomials.
     * @param divisor the Polynomial that this is being divided by
     * @return PolyQuotientRemainder a key-value pair type object. This object has the methods getQuotient() and getRemainder()
     * to access the respective parts. The quotient and the remainder are also Polynomials
     * @see PolyQuotientRemainder
     */
    public PolyQuotientRemainder divide(Polynomial divisor) {
        ArrayList<Term> quotientTerms = new ArrayList<Term>();
        Polynomial dividend = clone();

        /** Use the division algorithm to multiply the divisor by a factor (which is a term of the quotient) such that the 
         * leading terms of the dividend and the new divisor cancel out, and repeat until this isn't possible. */
        while (dividend.getDegree() >= divisor.getDegree()) {
            double quotientTermCoeff = dividend.getLeadingCoeff() / divisor.getLeadingCoeff();
            int quotientTermDegree = dividend.getDegree() - divisor.getDegree();
            Term quotientTerm = new Term(quotientTermCoeff, quotientTermDegree); 

            quotientTerms.add(quotientTerm);
            dividend = dividend.subtract(divisor.multiply(new Polynomial(quotientTerm)));
        }

        return new PolyQuotientRemainder(new Polynomial(quotientTerms), dividend);
    }

    /** 
     * Produces the result of the assertion that this Polynomial is equal to the other Polynomial.
     * @param otherPolynomial the Polynomial that this Polynomial is being compared to
     * @return whether or not the two Polynomials are equal
     * @see Term.equals
     */
    public boolean equals(Polynomial otherPolynomial) {
        if (terms.size() == otherPolynomial.terms().size()) {
            ArrayList<Term> otherPolynomialTerms = otherPolynomial.terms();

            for (int i = 0; i < terms.size(); i++) {
                if (!(terms.get(i).equals(otherPolynomialTerms.get(i)))) return false;
            }
            return true;
        }
        else return false;
    }

    /**
     * Produces the result of the polynomial raised to a power.
     * @param power by what the degree the polynomial is raised 
     * @return a polynomial that is this polynomial raised to the power
     */
    public Polynomial pow(int power) {
        /* Currently, this method is run iteratively. This is really slow for higher powers, but I suppose there isn't anyway to circumnavigate that */
        Polynomial result = clone();
        for (int i = 1; i < power; i++) {
            result = result.multiply(this);
        }
        return result;
    }

    /**
     * Produces a list of doubles containing approximations of the real roots of the polynomial.
     * This method uses Newton's Method to approximate the real roots, and does so recursively.
     * This function's time complexity is based on A. the degree of the polynomial and B. how far
     * the roots are from 0. 
     * @return a list of the real roots of the polynomial
     */
    public ArrayList<Double> realRoots() {
        ArrayList<Double> roots = new ArrayList<Double>();

        /* The root of a linear function ax+b is given by the formula -b/a */
        if (degree == 1) {
            roots.add(-terms.get(1).getCoefficient() / terms.get(0).getCoefficient());
        }
        else {
            /* For now, an initial guess of 0 will be taken. If the derivative at the guess is 0, then guess again */
            double guess = 0;

            /** The reason we don't use a guess at which the derivative is zero is because f'(x) is a denominator 
             * when finding the next x value. So, we choose a different value to prevent a divison by zero error */
            while (derivative().valueAt(guess) == 0) guess++;
            double x = guess;

            /* The core of Newton's method; we find an x value closer to the root using the previous x value */
            for (int i = 0; i < MathConstants.MAX_ITER; i++) {
                x = x - valueAt(x) / derivative().valueAt(x);

                /* If the value of the function at x is sufficiently close to 0, we can stop interating */
                if (MathUtil.isClose(valueAt(x), 0, MathConstants.EPSILON)) break;
            }
            /* Some values may be extremely close to the root (i.e. 2.99999999...), so they'll just be rounded off */
            if (MathUtil.isClose(x, MathUtil.roundToInt(x), MathConstants.ROUNDING_THRESHOLD)) x = MathUtil.roundToInt(x);
            roots.add(x);

            /** According to the factor theorem, the roots of a polynomial are also the factor when expressed as a linear factor
             * such that a root r of p(x) means that (x-r) is a factor of p(x) */
            Polynomial rootAsLinearFactor = new Polynomial(new Term(1, 1), new Term(-x, 0));

            /** We will recursively find the roots of the polynomial divided by the linear factor we found. We keep doing this until
             * the resulting polynomial has a degree of 1. A first degree polynomial is the base case, allowing us to find the root
             * by formula. The division allows us to exclude the root we just found, so there are no duplicates. */
            roots.addAll(this.divide(rootAsLinearFactor).getQuotient().realRoots());
        }
        /* If the function is a linear function, then nothing else is executed and an empty list is returned */
        Collections.sort(roots);
        return roots;
    }

}