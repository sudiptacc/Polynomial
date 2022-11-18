package polynomial;


import java.util.Comparator;

final class SortByDegree implements Comparator<Term> {

    /* 
     * Java needs a "comparator" class, so that a list can be sorted by a specific field of the objects.
     * In this case, we are checking by the degrees of the terms, so that the polynomial can be sorted to
     * standard form.
    */

    public int compare(Term firstTerm, Term secondTerm) {
        /* Return the negation, so that the sorting is done from greatest to least */
        return - (firstTerm.getDegree() - secondTerm.getDegree());
    }

}