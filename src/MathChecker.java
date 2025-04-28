/**
 * class that checks math.
 */
public class MathChecker {
    static final double COMPARISON_THRESHOLD = 0.000001;
    /**
     * @param a first num.
     * @param b second.
     * @return true if two numbers are different by 0.000001.
     */
    public static boolean doubleEquals(double a, double b) {
        return  Math.abs(a - b) < MathChecker.COMPARISON_THRESHOLD;
    }
}
