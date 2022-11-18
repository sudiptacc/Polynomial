package polynomial;
public class UserOfPolynomial {

    /* Quick testing command: 
     * javac UserOfPolynomial.java ; clear ; java UserOfPolynomial 
     */

    public static void main(String[] args) {
        Polynomial firstPolynomial = new Polynomial(new Term(1, 2), new Term(4, 0));
        System.out.println(firstPolynomial.divide(new Polynomial(new Term(1, 2))));
    }
}