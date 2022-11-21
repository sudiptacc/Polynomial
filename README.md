# Polynomial
 An OOP representation of polynomials, using Java.

# Capabilities
 ### MathUtil
  * Float equality (closeness) using tolerance
 ### Term
  Defined as a component of the Polynomial class
  * Most algebraic operations (except division) between terms
  * Evaluation of a term at a value
  * General derivative of a term
  * Negation of a term
  * Check for equality between terms
 ### Polynomial
  * All algebraic operations between polynomials
  * Automatic combination of like terms, and sorting into standard form
  * Evaluation of a polynomial at a value
  * General derivative of a polynomial
  * Negation of a polynomial
  * Check for equality between polynomials
  * Find real roots (Using Newton's Method)

# User Methods
 ### MathUtil
  * isClose
  * roundToInt
 ### Term
  * getDegree
  * getCoefficient
  * setDegree
  * setCoefficient
  * toString
  * valueAt
  * derivative
  * negation
  * add
  * subtract
  * multiply
  * pow
  * equals

### Polynomial
 * getDegree
 * getLeadingCoeff
 * getLeadingTerm
 * terms
 * clone
 * getTerm
 * addTerm
 * removeTerm
 * toString
 * valueAt
 * derivative
 * negation
 * add
 * subtract
 * multiply
 * pow
 * divide
 * equals
 * realRoots

# To Be Implemented
 ### Polynomial
  * Polynomial inequalities
  * Intervals for inequalities