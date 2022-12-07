package polynomial.src;

public class PolynomialParser {
    
    public static Term parseTerm(String term) {
        String[] parts = term.split("x\\^");
        double coefficient = Double.parseDouble(parts[0]);

        int degree;

        if (parts.length > 1) degree = Integer.parseInt(parts[1]);
        else degree = 0;

        return new Term(coefficient, degree);
    }

    public static Polynomial parseExpression(String expression) {
        /* To account for negatives */
        expression = expression.replace("-", "+-");
        String[] expressionTerms = expression.split("\\+");

        Term[] terms = new Term[expressionTerms.length];

        for (int i = 0; i < expressionTerms.length; i++)
            terms[i] = parseTerm(expressionTerms[i]);

        return new Polynomial(terms);
    }

}
