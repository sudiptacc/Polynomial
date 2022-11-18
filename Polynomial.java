package polynomial;
import java.util.ArrayList;

public class Polynomial {


    private ArrayList<Term> terms = new ArrayList<Term>();
    private int degree;
    private double leadingCoeff;
    private Term leadingTerm;

    public Polynomial(Term ... polyTerms) {
        /* Initialize the terms arraylist with the arguments */
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

    public Polynomial(ArrayList<Term> polyTerms) {
        /* Initialize the terms arraylist with the arguments */
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
     * Produces the general derivative of the Polynomial
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
     * Produces the negation of the Polynomial (all the terms are just negated)
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
     * @see MathUtil.sumOfTermLists 
     * @see combineLikeTerms
     */
    public Polynomial add(Polynomial addend) {        
        return new Polynomial(MathUtil.sumOfTermLists( terms, addend.terms() ));
    }

    /** 
     * Produces the difference of two Polynomials
     * @param subtrahend the Polynomial that is the subtrahend
     * @return the difference of the Polynomials
     * @see MathUtil.sumOfTermLists 
     * @see combineLikeTerms
     */
    public Polynomial subtract(Polynomial subtrahend) {
        return new Polynomial(MathUtil.sumOfTermLists(terms, subtrahend.negation().terms()));
    }

    /** 
     * Produces the product of two Polynomials
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
     * Produces the quotient and remainder of the division of two Polynomials
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
     * Produces the result of the assertion that this Polynomial is equal to the other Polynomial
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


    // public ??? realRoots() {
    //     return ???;
    // }
}