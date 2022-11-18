# Polynomial
 An OOP representation of polynomials, using Java.

### Note
 This was copied from my nft-server folder! Previous changes and additions aren't shown here.

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

# Methods
 ### MathUtil
  * isClose
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
  * equals

# Polynomial
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
 * divide
 * equals

# To Be Implemented
 ### Polynomial
  * Raising polynomials to a power
  * Polynomial inequalities
  * Finding real roots of polynomials