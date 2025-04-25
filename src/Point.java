/**
 * Represents a point in a two-dimensional coordinate system.
 * This class stores the x and y coordinates of a point.
 * It provides methods to retrieve and manipulate the point's coordinates.
 */
public class Point {
    private final double x;
    private final double y;
    /** Constructor.
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** Distance.
     * @param other The other Point coordinates.
     * @return the distance between points as a double.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow((this.x - other.x), 2) + Math.pow((this.y - other.y), 2));
    }

    /**Equals.
     * @param other The other Point coordinates.
     * @return if the points is equal true else false.
     */
    public boolean equals(Point other) {
        return this.x == other.x && this.y == other.y;
    }

    /**Accessor method for x.
     * @return x
     */
    public double getX() {
        return this.x;
    }
    /**Accessor method for y.
     * @return y
     */
    public double getY() {
        return this.y;
    }
}
