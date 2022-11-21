package polynomial.src;

/**
 * The modelling of the component of a Polynomial. A Term is defined as a part
 * of a Polynomial, such that the Polynomial is the sum of Terms. A term is
 * represented by its coefficient and degree, where the coefficient is a real
 * number and the degree is a whole number. Terms and Polynomials share many
 * operations, but not necessarily all of them.
 */
public class Term {
    
    private int degree;
    private double coefficient;

    public Term(double argCoeff, int argDegree) {
        degree = argDegree;
        coefficient = argCoeff;

        /* terms can only have a degree within the set of whole numbers. */
        if (degree < 0) throw new IllegalArgumentException("A term can only have a degree/exponent within the set of whole numbers.");
        
    }

    public int getDegree() {
        return degree;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setDegree(int newDegree) {
        degree = newDegree;
    }

    public void setCoefficient(double newCoefficient) {
        coefficient = newCoefficient;
    } 

    @Override
    public String toString() {
        if (coefficient == 0) return "0";
        else if (degree == 0) return coefficient + "";
        else return "(" + coefficient + ")" + "x" + "^" + degree;
    }

    /**
     * Produces the evaluation of the term at a certian x value.
     * @param a the value at which the term should be evaluated
     * @return the value of the term at x = a
     */
    public double valueAt(double a) {
        return coefficient * Math.pow(a, degree);
    }

    /**
     * Produces the general derivative of the term.
     * @return the term that is the general derivative of the term
     */
    public Term derivative() {
        if (degree == 0) return new Term(0, 0);
        else return new Term(coefficient * degree, degree - 1);
    }

    /**
     * Produces the negation of the term.
     * @return the term that is the negation of the term
     */
    public Term negation() {
        return new Term(-coefficient, degree);
    }

    /**
     * Produces the sum of two terms. Only defined for terms that have the same degrees.
     * @param addend the other term that is being added
     * @return a new term that is the sum of the two terms
     * @throws illegalArgumentException if the degrees of the terms are not equal
     */
    public Term add(Term addend) {
        if (degree == addend.getDegree()) {
            return new Term(coefficient + addend.getCoefficient(), degree);
        }
        else {
            throw new IllegalArgumentException("Addition between terms is only defined for same degree terms.");
        }
    }

    /**
     * Produces the difference of two terms. Only defined for terms that have the same degrees.
     * @param subtrahend the other term that is the subtrahend
     * @return a new term that is the difference of the two terms
     * @throws illegalArgumentException if the degrees of the terms are not equal
     */
    public Term subtract(Term subtrahend) {
        if (degree == subtrahend.getDegree()) {
            return new Term(coefficient - subtrahend.getCoefficient(), degree);
        }
        else {
            throw new IllegalArgumentException("Subtraction between terms is only defined for same degree terms.");
        }
    }

    /**
     * Produces the product of two terms.
     * @param multiplicand what the first term is multiplied by
     * @return a term that is the product of the other two terms
     */
    public Term multiply(Term multiplicand) {
        return new Term(coefficient * multiplicand.getCoefficient(), degree + multiplicand.getDegree());
    }

    /**
     * Produces the result of the term raised to a power.
     * @param power by what degree the term is raised
     * @return a term that is this term raised to the power
     */
    public Term pow(int power) {
        return new Term(Math.pow(coefficient, power), degree * power);
    }

    /**
     * I decided to not include division here, because the output of the division between
     * two terms is not always a term. This would mess with the definition of the
     * term class, so I decided that the division should be left for the Polynomial class.
     *
     * Comparison operators for terms are not very useful, considering
     * those sorts of problems work better for polynomials. A polynomial
     * can have a single term anyways, so no need for comparison.
     */
    
    /** 
     * Asserts if the two terms being compared are equal.
     * @param term the other term that is being checked
     * @return  whether or not the two terms are equal
     */
    public boolean equals(Term term) {
        return MathUtil.isClose(coefficient, term.getCoefficient(), MathConstants.EPSILON) && 
               degree == term.getDegree();
    }

}
